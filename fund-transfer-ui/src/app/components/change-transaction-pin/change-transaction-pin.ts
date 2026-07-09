import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-change-transaction-pin',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './change-transaction-pin.html',
  styleUrl: './change-transaction-pin.css'
})
export class ChangeTransactionPin {

  oldPin = '';
  newPin = '';
  confirmPin = '';

  constructor(private http: HttpClient) {}

  changePin() {

    if (this.newPin !== this.confirmPin) {
      alert('New PIN and Confirm PIN do not match');
      return;
    }

    const request = {
      userId: Number(sessionStorage.getItem('userId')),
      oldPin: this.oldPin,
      newPin: this.newPin
    };

    this.http.post(
      'http://localhost:8080/transaction-pin/change',
      request,
      { responseType: 'text' }
    ).subscribe({
      next: (res) => {
        alert(res);
      },
      error: () => {
        alert('Unable to change PIN');
      }
    });

  }

}