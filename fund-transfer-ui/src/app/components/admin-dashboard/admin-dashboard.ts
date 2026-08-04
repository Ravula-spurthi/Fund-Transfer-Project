import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css'
})
export class AdminDashboard {

  constructor(private router: Router, private adminService: AdminService) {}

  goToManageBeneficiaries(): void {
    this.router.navigate(['/admin/beneficiaries']);
  }

  logout() {

    sessionStorage.clear();

    alert("Logged Out Successfully");

    this.router.navigate(['/login']);

  }

}