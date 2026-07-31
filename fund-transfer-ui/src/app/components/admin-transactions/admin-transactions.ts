import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { Transaction } from '../../models/transaction';
import { TransactionService } from '../../core/services/transaction.service';

interface TransactionViewModel extends Transaction {
  direction: 'DEBIT' | 'CREDIT' | 'UNKNOWN';
  displayAmount: string;
}

@Component({
  selector: 'app-admin-transactions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-transactions.html',
  styleUrl: './admin-transactions.css'
})
export class AdminTransactions implements OnInit {

  transactions: TransactionViewModel[] = [];

  constructor(
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }

  ngOnInit(): void {

    this.transactionService.getAllTransactions().subscribe({

      next: (data) => {

        this.transactions = (data || []).map(txn => this.mapTransaction(txn));

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  private mapTransaction(txn: Transaction): TransactionViewModel {

    const mode = (txn.transactionMode || '').toUpperCase();

    const direction =
      mode === 'CREDIT'
        ? 'CREDIT'
        : mode === 'DEBIT'
        ? 'DEBIT'
        : 'UNKNOWN';

    return {

      ...txn,

      direction,

      displayAmount:

        direction === 'CREDIT'
          ? `+ ₹${txn.amount}`
          : direction === 'DEBIT'
          ? `- ₹${txn.amount}`
          : `₹${txn.amount}`

    };

  }

}