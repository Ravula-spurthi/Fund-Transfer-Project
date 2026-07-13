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

  transactionDate: string = '';

  selectAll = false;

  userId = Number(sessionStorage.getItem('userId')) || 1;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadStatement();
  }

  // Load all transactions
  loadStatement() {

    this.http.get<any[]>(
      `http://localhost:8080/statement?userId=${this.userId}`
    ).subscribe({

      next: (data) => {

        this.transactions = data.map(t => ({
          ...t,
          selected: false
        }));

        this.selectAll = false;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  // Filter by selected date
  filterTransactions() {

    if (!this.transactionDate) {

      this.loadStatement();

      return;

    }

    this.http.get<any[]>(
      `http://localhost:8080/statement?userId=${this.userId}&transactionDate=${this.transactionDate}`
    ).subscribe({

      next: (data) => {

        this.transactions = data.map(t => ({
          ...t,
          selected: false
        }));

        this.selectAll = false;

      },

      error: (err) => {

        console.error(err);

      }

    });

  }

  // Select/Deselect all
  toggleSelectAll() {

    this.transactions.forEach(t => {

      t.selected = this.selectAll;

    });

  }

  // Download selected transactions
  downloadSelectedPDF() {

    const selectedTransactions = this.transactions.filter(
      t => t.selected
    );

    if (selectedTransactions.length === 0) {

      alert("Please select at least one transaction.");

      return;

    }

    const doc = new jsPDF();

    doc.setFontSize(18);

    doc.text("Account Statement", 14, 20);

    autoTable(doc, {

      head: [[
        "Date",
        "Beneficiary",
        "Amount",
        "Status",
        "Type",
        "Balance",
        "Remarks"
      ]],

      body: selectedTransactions.map(t => [

        t.transactionDate,
        t.beneficiaryName,
        "₹ " + t.amount,
        t.status,
        t.transactionType,
        "₹ " + t.balance,
        t.remarks

      ]),

      startY: 30

    });

    doc.save("Account_Statement.pdf");

  }

}