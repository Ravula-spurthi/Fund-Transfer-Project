import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-manage-beneficiaries',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './manage-beneficiaries.html',
  styleUrl: './manage-beneficiaries.css'
})
export class ManageBeneficiaries implements OnInit {

  usersWithBeneficiaries: any[] = [];

  filteredUsers: any[] = [];

  selectedUser: any = null;

  beneficiaries: any[] = [];

  selectedBeneficiary: any = null;

  loading = true;

  error = '';

  searchText = '';

  showPopup = false;

  constructor(
    private adminService: AdminService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {

    this.loading = true;

    this.adminService.getUsersWithBeneficiaries().subscribe({

      next: (data: any[]) => {

        this.usersWithBeneficiaries = data;

        this.filteredUsers = [...data];

        this.loading = false;

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);

        this.error = "Unable to load users.";

        this.loading = false;

      }

    });

  }

  searchUsers(): void {

    const text = this.searchText.toLowerCase();

    this.filteredUsers = this.usersWithBeneficiaries.filter(user =>

      user.name.toLowerCase().includes(text) ||

      user.email.toLowerCase().includes(text) ||

      user.accountNumber.includes(text)

    );

  }

  selectUser(): void {

    if (this.selectedUser) {

      this.beneficiaries = this.selectedUser.beneficiaries;

    } else {

      this.beneficiaries = [];

    }

  }

  viewBeneficiary(beneficiary: any): void {

    this.selectedBeneficiary = beneficiary;

    this.showPopup = true;

  }

  closePopup(): void {

    this.showPopup = false;

  }

  goBack(): void {

    this.router.navigate(['/admin-dashboard']);

  }

}