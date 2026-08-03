import { Component, OnInit } from '@angular/core';
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

  // Popup variables
  selectedUser: User | null = null;
  showViewPopup: boolean = false;

  showEditPopup = false;

editUserData: User = {
  id: 0,
  name: '',
  email: '',
  mobile: '',
  accountNumber: '',
  balance: 0
};

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    console.log("Loading users...");

    this.adminService.getAllUsers().subscribe({
      next: (data) => {
        console.log("Users received:", data);
        this.users = data;
        this.filteredUsers = data;
      },
      error: (err) => {
        console.error("Error loading users", err);
      }
    });
  }

  searchUsers(): void {

    const text = this.searchText.toLowerCase();

    this.filteredUsers = this.users.filter(user =>
      user.name.toLowerCase().includes(text) ||
      user.email.toLowerCase().includes(text) ||
      user.accountNumber.includes(text)
    );
  }

  // View User
  viewUser(user: User): void {
    this.selectedUser = user;
    this.showViewPopup = true;
  }

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

      console.log(err);

      alert("Update Failed");

    }

  });

}

deleteUser(id: number): void {

  if (!confirm("Delete this user?")) {

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

  // Close Popup
  closePopup(): void {
    this.showViewPopup = false;
    this.selectedUser = null;
  }

}