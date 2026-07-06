import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css'
})
export class ForgotPassword {

  email = '';
  mobile = '';
  newPassword = '';
  confirmPassword = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  updatePassword() {

    if (
      this.email.trim() == '' ||
      this.mobile.trim() == '' ||
      this.newPassword.trim() == '' ||
      this.confirmPassword.trim() == ''
    ) {
      alert("Please fill all fields");
      return;
    }

    if (this.newPassword != this.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    const forgotPasswordData = {
      email: this.email,
      mobile: this.mobile,
      newPassword: this.newPassword
    };

    this.http.post(
      'http://localhost:8080/api/auth/forgot-password',
      forgotPasswordData,
      { responseType: 'text' }
    ).subscribe({

      next: (response) => {
        alert(response);
        this.router.navigate(['/login']);
      },

      error: (err) => {
        alert(err.error);
      }

    });

  }

}