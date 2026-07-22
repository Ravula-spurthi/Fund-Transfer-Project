 import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dashboard } from '../../models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/dashboard';

  constructor(private http: HttpClient) {}

  // Dashboard
  getDashboard(userId: number): Observable<Dashboard> {
    return this.http.get<Dashboard>(`${this.apiUrl}/${userId}`);
  }

  // Balance
  getBalance(data: any): Observable<number> {
    return this.http.post<number>(
      `${this.apiUrl}/balance`,
      data
    );
  }

  // Monthly Transactions Chart
  getMonthlyTransactions(userId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/monthly-transactions/${userId}`
    );
  }

}