import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard {

  constructor(private router: Router) {}

  goToTransfer() {
    this.router.navigate(['/transfer-money']);
  }

  goToBeneficiary() {
    this.router.navigate(['/add-beneficiary']);
  }
  goToTransactions(){
    this.router.navigate(['/transactions']);
  }
  goToProfile() {
  this.router.navigate(['/profile']);
  }
  goToStatement() {
  this.router.navigate(['/statement']);
  }
  logout() {
  this.router.navigate(['/']);
  }
}