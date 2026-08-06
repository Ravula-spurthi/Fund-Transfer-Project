import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../models/user';
import { Beneficiary } from '../models/beneficiary';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private apiUrl = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) {}

  // ============================
  // USERS
  // ============================

  getAllUsers(): Observable<User[]> {

    return this.http.get<User[]>(
      `${this.apiUrl}/users`
    );

  }

  getUsersWithBeneficiaries(): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.apiUrl}/users-with-beneficiaries`
    );

  }

  updateUser(user: User): Observable<User> {

    return this.http.put<User>(
      `${this.apiUrl}/users/${user.id}`,
      user
    );

  }

  deleteUser(id: number): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/users/${id}`,
      {
        responseType: 'text'
      }
    );

  }

  // ============================
  // BENEFICIARIES
  // ============================

  updateBeneficiary(
    id: number,
    beneficiary: Beneficiary
  ): Observable<Beneficiary> {

    return this.http.put<Beneficiary>(
      `http://localhost:8080/beneficiaries/${id}`,
      beneficiary
    );

  }

  deleteBeneficiary(
    id: number
  ): Observable<string> {

    return this.http.delete(
      `http://localhost:8080/beneficiaries/${id}`,
      {
        responseType: 'text'
      }
    );

  }

}