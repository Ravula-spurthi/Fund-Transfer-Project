import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Transaction } from '../../models/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  private apiUrl = 'http://localhost:8080/transactions';

  constructor(private http: HttpClient) {}

  // ==========================
  // USER TRANSACTIONS
  // ==========================
  getTransactions(userId: number): Observable<Transaction[]> {

    return this.http.get<Transaction[]>(
      `${this.apiUrl}/user/${userId}`
    );

  }

  // ==========================
  // ADMIN - ALL TRANSACTIONS
  // ==========================
  getAllTransactions(): Observable<Transaction[]> {

    return this.http.get<Transaction[]>(this.apiUrl);

  }

  // ==========================
  // ADMIN - SEARCH USER
  // ==========================
  searchTransactions(user: string): Observable<Transaction[]> {

    return this.http.get<Transaction[]>(
      `${this.apiUrl}/search?user=${user}`
    );

  }

  // ==========================
  // ADMIN - DATE FILTER
  // ==========================
  filterTransactions(
    from: string,
    to: string
  ): Observable<Transaction[]> {

    return this.http.get<Transaction[]>(
      `${this.apiUrl}/filter?from=${from}&to=${to}`
    );

  }

}