import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-set-pin',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './set-transaction-pin.html',
  styleUrl: './set-transaction-pin.css'
})
export class SetTransactionPin {

  pin = '';
  confirmPin = '';

  constructor(private http: HttpClient) {}

  savePin() {

    if (this.pin !== this.confirmPin) {
      alert("PINs do not match");
      return;
    }

    const request = {
      userId: Number(sessionStorage.getItem('userId')),
      newPin: this.pin
    };

    this.http.post(
      'http://localhost:8080/transaction-pin/set',
      request,
      { responseType: 'text' }
    ).subscribe({
      next: (res) => {
        alert(res);
      },
      error: () => {
        alert("Unable to save PIN");
      }
    });

  }

}