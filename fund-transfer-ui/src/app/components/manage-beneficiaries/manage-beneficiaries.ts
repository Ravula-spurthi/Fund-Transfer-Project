import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
  loading = true;
  error = '';

  constructor(
    private adminService: AdminService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {

    this.loading = true;
    this.error = '';

    this.adminService.getUsersWithBeneficiaries().subscribe({

      next: (data: any[]) => {

        console.log(data);

        this.usersWithBeneficiaries = data;

        this.loading = false;

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);

        this.error = 'Failed to load users.';

        this.loading = false;

        this.cdr.detectChanges();

      }

    });

  }

}