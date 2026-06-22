import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transfer-money',
  standalone: true,
  templateUrl: './transfer-money.html',
  styleUrl: './transfer-money.css'
})
export class TransferMoney {

  constructor(private router: Router) {}

  transfer() {
    this.router.navigate(['/otp-verification']);
  }

  resetForm() {
    location.reload();
  }
}