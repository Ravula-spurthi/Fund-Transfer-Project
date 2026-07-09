import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../core/services/dashboard.service';
import { Dashboard as DashboardModel } from '../../models/dashboard';
import {RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  dashboard!: DashboardModel;

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

  goToProfile() {
    this.router.navigate(['/profile']);
  }

  goToStatement() {
    this.router.navigate(['/statement']);
  }

  logout() {
    sessionStorage.clear();
    this.router.navigate(['/']);
  }

}