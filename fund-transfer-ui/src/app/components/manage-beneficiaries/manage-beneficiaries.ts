import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { AdminService } from '../../services/admin.service';
import { Beneficiary } from '../../models/beneficiary';

@Component({
  selector: 'app-manage-beneficiaries',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './manage-beneficiaries.html',
  styleUrl: './manage-beneficiaries.css'
})
export class ManageBeneficiaries implements OnInit {

  // ==========================
  // Master Data
  // ==========================

  usersWithBeneficiaries: any[] = [];

  filteredUsers: any[] = [];

  beneficiaries: Beneficiary[] = [];

  filteredBeneficiaries: Beneficiary[] = [];

  selectedUser: any = null;

  // ==========================
  // Summary Cards
  // ==========================

  totalUsers = 0;

  totalBeneficiaries = 0;

  selectedUserBeneficiaries = 0;

  // ==========================
  // Search
  // ==========================

  userSearch = '';

  beneficiarySearch = '';

  // ==========================
  // Pagination
  // ==========================

  currentPage = 1;

  pageSize = 10;

  totalPages = 1;

  paginatedBeneficiaries: Beneficiary[] = [];

  // ==========================
  // Loading
  // ==========================

  loading = true;

  error = '';

  // ==========================
  // View Popup
  // ==========================

  showPopup = false;

  selectedBeneficiary: Beneficiary | null = null;

  // ==========================
  // Edit Popup
  // ==========================

  showEditPopup = false;

  editBeneficiary!: Beneficiary;

  constructor(
    private adminService: AdminService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.loadUsers();

  }

  // ===================================
  // Load Users
  // ===================================

  loadUsers(): void {

    this.loading = true;

    this.adminService.getUsersWithBeneficiaries()

      .subscribe({

        next: (data) => {

          this.usersWithBeneficiaries = data;

          this.filteredUsers = [...data];

          this.totalUsers = data.length;

          this.totalBeneficiaries = 0;

          data.forEach(user => {

            this.totalBeneficiaries +=

              user.beneficiaries.length;

          });

          this.loading = false;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.error = 'Unable to load users';

          this.loading = false;

        }

      });

  }

  // ===================================
  // Search User
  // ===================================

  searchUsers(): void {

    const text = this.userSearch

      .toLowerCase()

      .trim();

    if (!text) {

      this.filteredUsers = [

        ...this.usersWithBeneficiaries

      ];

      return;

    }

    this.filteredUsers =

      this.usersWithBeneficiaries.filter(

        user =>

          user.name.toLowerCase()

            .includes(text)

          ||

          user.email.toLowerCase()

            .includes(text)

          ||

          user.accountNumber

            .includes(text)

      );

  }

  // ===================================
  // Select User
  // ===================================

  selectUser(): void {

    if (!this.selectedUser) {

      this.beneficiaries = [];

      this.filteredBeneficiaries = [];

      this.selectedUserBeneficiaries = 0;

      this.updatePagination();

      return;

    }

    this.beneficiaries =

      this.selectedUser.beneficiaries;

    this.filteredBeneficiaries =

      [...this.beneficiaries];

    this.selectedUserBeneficiaries =

      this.beneficiaries.length;

    this.currentPage = 1;

    this.updatePagination();

  }

  // ===================================
  // Search Beneficiaries
  // ===================================

  searchBeneficiaries(): void {

    const text = this.beneficiarySearch

      .toLowerCase()

      .trim();

    if (!text) {

      this.filteredBeneficiaries =

        [...this.beneficiaries];

      this.currentPage = 1;

      this.updatePagination();

      return;

    }

    this.filteredBeneficiaries =

      this.beneficiaries.filter(

        b =>

          b.beneficiaryName

            .toLowerCase()

            .includes(text)

          ||

          b.bankName

            .toLowerCase()

            .includes(text)

          ||

          b.accountNumber

            .includes(text)

          ||

          b.ifscCode

            .toLowerCase()

            .includes(text)

      );

    this.currentPage = 1;

    this.updatePagination();

  }

  // ===================================
  // Pagination
  // ===================================

  updatePagination(): void {

    this.totalPages = Math.ceil(

      this.filteredBeneficiaries.length /

      this.pageSize

    );

    if (this.totalPages === 0) {

      this.totalPages = 1;

    }

    const start =

      (this.currentPage - 1) * this.pageSize;

    const end =

      start + this.pageSize;

    this.paginatedBeneficiaries =

      this.filteredBeneficiaries.slice(

        start,

        end

      );

  }

  previousPage(): void {

    if (this.currentPage > 1) {

      this.currentPage--;

      this.updatePagination();

    }

  }

  nextPage(): void {

    if (this.currentPage < this.totalPages) {

      this.currentPage++;

      this.updatePagination();

    }

  }

  goToPage(page: number): void {

    this.currentPage = page;

    this.updatePagination();

  }

  getPageNumbers(): number[] {

    return Array(this.totalPages)

      .fill(0)

      .map((x, i) => i + 1);

  }

  // ===================================
  // View Beneficiary
  // ===================================

  viewBeneficiary(

    beneficiary: Beneficiary

  ): void {

    this.selectedBeneficiary = beneficiary;

    this.showPopup = true;

  }

  closePopup(): void {

    this.showPopup = false;

    this.selectedBeneficiary = null;

  }

  // ===================================
  // Edit Beneficiary
  // ===================================

  openEditPopup(

    beneficiary: Beneficiary

  ): void {

    this.editBeneficiary = {

      ...beneficiary

    };

    this.showEditPopup = true;

  }

  closeEditPopup(): void {

    this.showEditPopup = false;

  }

  saveBeneficiary(): void {

    this.adminService.updateBeneficiary(

      this.editBeneficiary.id,

      this.editBeneficiary

    ).subscribe({

      next: () => {

        alert(

          "Beneficiary Updated Successfully"

        );

        this.showEditPopup = false;

        this.loadUsers();

        if (this.selectedUser) {

          const selectedId =

            this.selectedUser.id;

          this.selectedUser =

            this.usersWithBeneficiaries.find(

              u => u.id === selectedId

            );

          this.selectUser();

        }

      },

      error: (err) => {

        console.error(err);

        alert(

          "Unable to update beneficiary."

        );

      }

    });

  }

  // ===================================
  // Delete Beneficiary
  // ===================================

  deleteBeneficiary(

    beneficiary: Beneficiary

  ): void {

    const confirmDelete = confirm(

      "Are you sure you want to delete this beneficiary?"

    );

    if (!confirmDelete) {

      return;

    }

    this.adminService.deleteBeneficiary(

      beneficiary.id

    ).subscribe({

      next: () => {

        alert(

          "Beneficiary Deleted Successfully"

        );

        this.loadUsers();

        if (this.selectedUser) {

          const selectedId =

            this.selectedUser.id;

          this.selectedUser =

            this.usersWithBeneficiaries.find(

              u => u.id === selectedId

            );

          this.selectUser();

        }

      },

      error: (err) => {

        console.error(err);

        alert(

          "Unable to delete beneficiary."

        );

      }

    });

  }

  // ===================================
  // Refresh Beneficiaries
  // ===================================

  refreshSelectedUser(): void {

    if (!this.selectedUser) {

      return;

    }

    const id = this.selectedUser.id;

    this.selectedUser = this.usersWithBeneficiaries.find(

      user => user.id === id

    );

    if (this.selectedUser) {

      this.selectUser();

    }

  }

  // ===================================
  // Clear User Search
  // ===================================

  clearUserSearch(): void {

    this.userSearch = '';

    this.filteredUsers = [

      ...this.usersWithBeneficiaries

    ];

  }

  // ===================================
  // Clear Beneficiary Search
  // ===================================

  clearBeneficiarySearch(): void {

    this.beneficiarySearch = '';

    this.filteredBeneficiaries = [

      ...this.beneficiaries

    ];

    this.currentPage = 1;

    this.updatePagination();

  }

  // ===================================
  // Dashboard Navigation
  // ===================================

  goBack(): void {

    this.router.navigate([

      '/admin-dashboard'

    ]);

  }

}