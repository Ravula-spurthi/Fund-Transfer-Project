import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Transaction } from '../../../models/transaction';
import { TransactionService } from '../../../core/services/transaction.service';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-history.html',
  styleUrl: './transaction-history.css'
})
export class TransactionHistory implements OnInit {

  transactions: Transaction[] = [];

  constructor(
    private transactionService: TransactionService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.transactionService.getTransactions().subscribe({

      next: (data) => {
        console.log('API Response:', data);
        this.transactions = data;
        this.cdr.detectChanges();
      },

      error: (err) => {
        console.error(err);
      }

    });

  }

}