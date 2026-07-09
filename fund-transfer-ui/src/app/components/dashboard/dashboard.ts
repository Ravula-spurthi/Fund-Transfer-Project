import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../core/services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  dashboard: any = {};

  userName = sessionStorage.getItem('name') || 'Customer';

  constructor(
    private router: Router,
    private dashboardService: DashboardService
  ) {}

  ngOnInit(): void {

    const userId = Number(sessionStorage.getItem('userId')) || 3;

    this.dashboardService.getDashboard(userId).subscribe({

      next: (data) => {

        this.dashboard = data;

        sessionStorage.setItem(
          'balance',
          String(data.balance)
        );

      },

      error: (err) => {
        console.log(err);
      }

    });

  }

  goToBeneficiary() {
    this.router.navigate(['/add-beneficiary']);
  }

  goToTransfer() {
    this.router.navigate(['/transfer-money']);
  }

  goToTransactions() {
    this.router.navigate(['/transactions']);
  }

  goToStatement() {
    this.router.navigate(['/statement']);
  }

  goToProfile() {
    this.router.navigate(['/profile']);
  }

  goToSetPin() {
    this.router.navigate(['/set-pin']);
  }

  goToChangePin() {
    this.router.navigate(['/change-pin']);
  }

  logout() {
    sessionStorage.clear();
    this.router.navigate(['/']);
  }

}