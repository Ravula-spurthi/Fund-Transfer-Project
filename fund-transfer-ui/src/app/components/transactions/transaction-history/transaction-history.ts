import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction-history.html',
  styleUrl: './transaction-history.css'
})
export class TransactionHistory {

  transactions = [
    {
      date: '19-06-2026',
      beneficiary: 'Spurthi',
      amount: 5000,
      status: 'Success'
    },
    {
      date: '18-06-2026',
      beneficiary: 'Pooja',
      amount: 2000,
      status: 'Success'
    },
    {
      date: '17-06-2026',
      beneficiary: 'Bhavana',
      amount: 1000,
      status: 'Failed'
    }
  ];

}