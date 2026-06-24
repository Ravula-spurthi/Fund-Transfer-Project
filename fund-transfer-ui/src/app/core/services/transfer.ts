import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  private apiUrl = 'http://localhost:8080/transfer';

  constructor(private http: HttpClient) {}

  transferMoney(data: any) {
    return this.http.post(
      this.apiUrl,
      data,
      { responseType: 'text' }
    );
  }
}