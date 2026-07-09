import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  // Backend Base URL
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Load Beneficiaries
  getBeneficiaries(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/beneficiaries`);
  }

  // Fund Transfer
  transferMoney(request: any): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/api/fund-transfer`,
      request,
      {
        responseType: 'text'
      }
    );
  }
}