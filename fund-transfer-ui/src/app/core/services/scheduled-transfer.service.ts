import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScheduledTransferService {

  private apiUrl = 'http://localhost:8080/scheduled-transfer';

  constructor(private http: HttpClient) {}

  getAllTransfers(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }

  saveTransfer(data: any): Observable<any> {
    return this.http.post(this.apiUrl, data);
  }

  updateTransfer(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, data);
  }

  deleteTransfer(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  executeTransfer(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${id}/execute`, {});
  }

}