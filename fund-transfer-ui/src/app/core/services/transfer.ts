import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  constructor(private http: HttpClient) {}

  getBeneficiaries() {
    return this.http.get<any[]>("http://localhost:8080/beneficiaries");
  }

  transferMoney(data: any) {
    return this.http.post(
      "http://localhost:8080/api/fund-transfer",
      data,
      { responseType: 'text' }
    );
  }

}