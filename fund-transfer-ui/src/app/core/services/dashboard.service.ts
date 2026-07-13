import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dashboard } from '../../models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/dashboard';

  constructor(private http: HttpClient) { }

  // Load dashboard details
  getDashboard(userId: number): Observable<Dashboard> {
    return this.http.get<Dashboard>(`${this.apiUrl}/${userId}`);
  }

  // Verify Transaction PIN and return balance
  getBalance(data: any): Observable<number> {
    return this.http.post<number>(
      `${this.apiUrl}/balance`,
      data
    );
  }

}