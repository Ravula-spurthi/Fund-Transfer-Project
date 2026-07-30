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

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.adminService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.filteredUsers = data;
      },
      error: (err) => {
        console.error('Error loading users', err);
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

}