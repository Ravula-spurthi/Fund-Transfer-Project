import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AdminService } from '../../services/admin.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-admin-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-users.html',
  styleUrl: './admin-users.css'
})
export class AdminUsers implements OnInit {

  users: User[] = [];
  filteredUsers: User[] = [];

  searchText: string = '';

  loading = true;

  // View Popup
  selectedUser: User | null = null;
  showViewPopup = false;

  // Edit Popup
  showEditPopup = false;

  editUserData: User = {
    id: 0,
    name: '',
    email: '',
    mobile: '',
    accountNumber: '',
    balance: 0
  };

  constructor(
    private adminService: AdminService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {

    this.loading = true;

    this.adminService.getAllUsers().subscribe({

      next: (data) => {

        console.log("Users received:", data);

        this.users = Array.isArray(data) ? data : [];

        this.searchUsers();

        this.loading = false;

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error("Error loading users", err);

        this.users = [];
        this.filteredUsers = [];

        this.loading = false;

      }

    });

  }

  searchUsers(): void {

    const text = this.searchText?.toLowerCase().trim() ?? '';

    if (!text) {

      this.filteredUsers = [...this.users];

      return;

    }

    this.filteredUsers = this.users.filter(user =>

      (user.name ?? '').toLowerCase().includes(text) ||

      (user.email ?? '').toLowerCase().includes(text) ||

      (user.accountNumber ?? '').toLowerCase().includes(text)

    );

  }

  clearSearch(): void {

    this.searchText = '';

    this.filteredUsers = [...this.users];

  }

  // View User

  viewUser(user: User): void {

    this.selectedUser = user;

    this.showViewPopup = true;

  }

  closePopup(): void {

    this.showViewPopup = false;

    this.selectedUser = null;

  }

  // Edit User

  editUser(user: User): void {

    this.editUserData = { ...user };

    this.showEditPopup = true;

  }

  saveUser(): void {

    this.adminService.updateUser(this.editUserData).subscribe({

      next: () => {

        alert("User Updated Successfully");

        this.showEditPopup = false;

        this.loadUsers();

      },

      error: (err) => {

        console.error(err);

        alert("Update Failed");

      }

    });

  }

  // Delete User

  deleteUser(id: number): void {

    if (!confirm("Are you sure you want to delete this user?")) {

      return;

    }

    this.adminService.deleteUser(id).subscribe({

      next: (message) => {

        alert(message);

        this.loadUsers();

      },

      error: (err) => {

        console.error(err);

        alert("Delete Failed");

      }

    });

  }

}