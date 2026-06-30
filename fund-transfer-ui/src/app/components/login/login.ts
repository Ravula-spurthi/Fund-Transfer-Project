import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
 
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    HttpClientModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
 
  email: string = '';
  password: string = '';
 
  constructor(
    private router: Router,
    private http: HttpClient
  ) {}
 
  onlogin() {
 
    console.log('Email:', this.email);
    console.log('Password:', this.password);
 
    const loginData = {
      email: this.email,
      password: this.password
    };
 
    this.http.post(
      'http://localhost:8080/login',
      loginData,
      { responseType: 'text' }
    ).subscribe({
      next: (response) => {
 
        console.log('Success:', response);
 
        sessionStorage.setItem('isLoggedIn', 'true');
        sessionStorage.setItem('email', this.email);
 
        alert('Login Successful');
 
        this.router.navigate(['/dashboard']);
      },
 
      error: (error) => {
 
        console.log('Error:', error);
 
        if (error.error) {
          alert(error.error);
        } else {
          alert('Invalid Credentials');
        }
      }
    });
  }
}
 