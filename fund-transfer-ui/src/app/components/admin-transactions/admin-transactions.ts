import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { Transaction } from '../../models/transaction';
import { User } from '../../models/user';

import { TransactionService } from '../../core/services/transaction.service';
import { AdminService } from '../../services/admin.service';

interface TransactionViewModel extends Transaction {
  direction: 'DEBIT' | 'CREDIT' | 'UNKNOWN';
  displayAmount: string;
}

@Component({
  selector: 'app-admin-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-transactions.html',
  styleUrl: './admin-transactions.css'
})
export class AdminTransactions implements OnInit {

  transactions: TransactionViewModel[] = [];
  filteredTransactions: TransactionViewModel[] = [];

  users: User[] = [];

  selectedUser = '';
  selectedRange = 'ALL';

  constructor(
    private transactionService: TransactionService,
    private adminService: AdminService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {}

  goBack(): void {
    this.router.navigate(['/admin-dashboard']);
  }

  ngOnInit(): void {

    this.loadTransactions();

    this.adminService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => {
        console.error(err);
      }
    });

  }

  loadTransactions(): void {

    this.transactionService.getAllTransactions().subscribe({

      next: (data) => {

        this.transactions = (data || []).map(txn => this.mapTransaction(txn));
        this.filteredTransactions = [...this.transactions];

        this.cdr.detectChanges();

      },

      error: (err) => {
        console.error(err);
      }

    });

  }

  private mapTransaction(txn: Transaction): TransactionViewModel {

    const type = (txn.transactionType || '').toUpperCase();

    const direction =
      type === 'CREDIT'
        ? 'CREDIT'
        : type === 'DEBIT'
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

  applyFilters(): void {

    if (this.selectedUser !== '') {

      this.transactionService.searchTransactions(this.selectedUser)
        .subscribe(data => {

          this.transactions = data.map(t => this.mapTransaction(t));
          this.filteredTransactions = [...this.transactions];

        });

      return;

    }

    if (this.selectedRange === 'ALL') {

      this.loadTransactions();
      return;

    }

    const today = new Date();
    const from = new Date();

    switch (this.selectedRange) {

      case '7':
        from.setDate(today.getDate() - 7);
        break;

      case '30':
        from.setMonth(today.getMonth() - 1);
        break;

      case '90':
        from.setMonth(today.getMonth() - 3);
        break;

      case '180':
        from.setMonth(today.getMonth() - 6);
        break;

      case '365':
        from.setFullYear(today.getFullYear() - 1);
        break;

    }

    this.transactionService
      .filterTransactions(
        from.toISOString().substring(0, 10),
        today.toISOString().substring(0, 10)
      )
      .subscribe(data => {

        this.transactions = data.map(t => this.mapTransaction(t));
        this.filteredTransactions = [...this.transactions];

      });

  }

}