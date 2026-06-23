import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BeneficiaryService {

  private apiUrl = 'http://localhost:8080/beneficiaries';

  constructor(private http: HttpClient) {}

  getBeneficiaries(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  addBeneficiary(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  deleteBeneficiary(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}