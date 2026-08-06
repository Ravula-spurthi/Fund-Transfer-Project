import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Transaction } from '../../../models/transaction';
import { TransactionService } from '../../../core/services/transaction.service';

interface TransactionViewModel extends Transaction {
  direction: 'DEBIT' | 'CREDIT' | 'UNKNOWN';
  displayAmount: string;
}

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-history.html',
  styleUrl: './transaction-history.css'
})
export class TransactionHistory implements OnInit {

  transactions: TransactionViewModel[] = [];
  filteredTransactions: TransactionViewModel[] = [];

searchText: string = '';

  constructor(
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }

  ngOnInit(): void {

    const userId = Number(sessionStorage.getItem('userId'));

    //console.log('Logged In User ID:', userId);

    this.transactionService.getTransactions(userId).subscribe({

      next: (data) => {
        console.log('API Response:', data);
        this.transactions = (data || []).map((txn) => this.mapTransaction(txn));
        this.filteredTransactions = [...this.transactions];
        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error(err);
      }

    });

  }

  private mapTransaction(txn: Transaction): TransactionViewModel {
    const mode = (txn.transactionMode || '').toUpperCase();
    const userId = Number(sessionStorage.getItem('userId'));
    const direction = mode === 'CREDIT' ? 'CREDIT' : mode === 'DEBIT' ? 'DEBIT' : 'UNKNOWN';

    return {
      ...txn,
      direction,
      displayAmount: direction === 'CREDIT'
        ? `+ ₹${txn.amount}`
        : direction === 'DEBIT'
          ? `- ₹${txn.amount}`
          : `₹${txn.amount}`
    };
  }
  searchTransactions(): void {

  const search = this.searchText.toLowerCase().trim();

  if (!search) {

    this.filteredTransactions = [...this.transactions];

    return;

  }

  this.filteredTransactions = this.transactions.filter(txn =>

    (txn.beneficiaryName || '')
      .toLowerCase()
      .includes(search)

    ||

    (txn.status || '')
      .toLowerCase()
      .includes(search)

    ||

    (txn.transactionDate || '')
      .toString()
      .toLowerCase()
      .includes(search)

  );

}

}