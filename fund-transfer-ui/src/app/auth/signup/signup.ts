import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  templateUrl: './signup.html',
  styleUrl: './signup.css'
})
export class Signup {

  name = '';
  email = '';
  mobile = '';
  accountNumber = '';
  password = '';
  confirmPassword = '';
  balance = 0;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  register() {

    if (
      this.name == '' ||
      this.email == '' ||
      this.mobile == '' ||
      this.accountNumber == '' ||
      this.password == '' ||
      this.confirmPassword == ''
    ) {
      alert("Please fill all fields");
      return;
    }

    if (this.password != this.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    const registerData = {
      name: this.name,
      email: this.email,
      mobile: this.mobile,
      accountNumber: this.accountNumber,
      password: this.password,
      balance: this.balance
    };
    console.log(registerData);
    this.http.post(
      'http://localhost:8080/api/auth/register',
      registerData,
      { responseType: 'text' }
    ).subscribe({

      next: (response) => {
        alert(response);
        this.router.navigate(['/login']);
      },

      error: (err) => {
  console.log("Full Error:", err);
  console.log("Status:", err.status);
  console.log("Error Body:", err.error);
  alert(JSON.stringify(err.error));
}

    });

  }

}