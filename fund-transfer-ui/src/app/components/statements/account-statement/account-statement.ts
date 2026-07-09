import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Component({
  selector: 'app-account-statement',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './account-statement.html',
  styleUrl: './account-statement.css'
})
export class AccountStatement implements OnInit {

  transactions: any[] = [];

  fromDate: string = '';
  toDate: string = '';

  userId = 1;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadStatement();
  }

  loadStatement() {

    this.http.get<any[]>(
      `http://localhost:8080/statement?userId=${this.userId}`
    ).subscribe({

      next: (data) => {
        this.transactions = data;
      },

      error: (err) => {
        console.error(err);
      }

    });

  }

  filterTransactions() {

    this.http.get<any[]>(
      `http://localhost:8080/statement?userId=${this.userId}&fromDate=${this.fromDate}&toDate=${this.toDate}`
    ).subscribe({

      next: (data) => {
        this.transactions = data;
      },

      error: (err) => {
        console.error(err);
      }

    });

  }

  downloadPDF() {

    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text('Account Statement', 14, 20);

    autoTable(doc, {

      head: [[
        'Date',
        'Beneficiary',
        'Amount',
        'Status',
        'Type',
        'Balance',
        'Remarks'
      ]],

      body: this.transactions.map(t => [
        t.transactionDate,
        t.beneficiaryName,
        t.amount,
        t.status,
        t.transactionType,
        t.balance,
        t.remarks
      ]),

      startY: 30

    });

    doc.save('Account_Statement.pdf');

  }

}