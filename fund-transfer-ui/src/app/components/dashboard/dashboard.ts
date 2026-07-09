import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../core/services/dashboard.service';
import { Dashboard as DashboardModel } from '../../models/dashboard';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  dashboard!: DashboardModel;

  constructor(
    private router: Router,
    private dashboardService: DashboardService
  ) {}

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
  this.dashboardService.getDashboard(3).subscribe({
    next: (data: DashboardModel) => {
      console.log(data);
      this.dashboard = data;
      console.log(this.dashboard);
    },
    error: (err: any) => {
      console.error(err);
    }
  });
}

  goToTransfer() {
    this.router.navigate(['/transfer-money']);
  }

  goToBeneficiary() {
    this.router.navigate(['/add-beneficiary']);
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
    this.router.navigate(['/']);
  }
}