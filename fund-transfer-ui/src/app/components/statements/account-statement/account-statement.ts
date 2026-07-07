import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-account-statement',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './account-statement.html',
  styleUrl: './account-statement.css'
})
export class AccountStatement implements OnInit {

  transactions: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.loadStatement();
  }

  loadStatement() {

    this.http.get<any[]>("http://localhost:8080/statement?userId=1")
      .subscribe({
        next: (response) => {
          console.log(response);
          this.transactions = response;
        },
        error: (error) => {
          console.log(error);
        }
      });

  }

}