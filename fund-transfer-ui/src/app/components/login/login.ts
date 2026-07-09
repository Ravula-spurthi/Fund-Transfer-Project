import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    RouterModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  email = '';
  password = '';

  role = 'user';

  captcha = '';
  enteredCaptcha = '';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.generateCaptcha();
  }

  generateCaptcha() {
    this.captcha = Math.floor(
      1000 + Math.random() * 9000
    ).toString();
  }

  refreshCaptcha() {
    this.generateCaptcha();
    this.enteredCaptcha = '';
  }

  onlogin() {

    if (this.email.trim() == '') {
      alert("Enter Email");
      return;
    }

    if (this.password.trim() == '') {
      alert("Enter Password");
      return;
    }

    if (this.enteredCaptcha != this.captcha) {
      alert("Invalid CAPTCHA");
      this.refreshCaptcha();
      return;
    }

    if (this.role == "admin") {

      if (this.email == "admin@fund.com"
          && this.password == "admin123") {

        sessionStorage.setItem("role", "admin");

        alert("Admin Login Successful");

        this.router.navigate(['/dashboard']);
        return;
      }
      else{
        alert("Invalid Admin Credentials");
        return;
      }

    }

    const loginData = {

      email: this.email,
      password: this.password

    };
    console.log(loginData);
    this.http.post<any>(
      'http://localhost:8080/api/auth/login',
      loginData
    ).subscribe({

      next: (response) => {

        sessionStorage.setItem(
          'isLoggedIn',
          'true'
        );

        sessionStorage.setItem(
          'email',
          response.email
        );

        sessionStorage.setItem(
          'name',
          response.name
        );

        sessionStorage.setItem(
          'userId',
          response.id
        );

        sessionStorage.setItem(
          'accountNumber',
          response.accountNumber
        );

        sessionStorage.setItem(
          'balance',
          response.balance
        );

        sessionStorage.setItem(
          'role',
          'user'
        );

        alert("Login Successful");

        this.router.navigate(['/dashboard']);

      },

      error: (err) => {

        alert("Invalid Email or Password");

        console.log(err);

      }

    });

  }

}