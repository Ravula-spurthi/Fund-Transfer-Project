import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { BeneficiaryService } from '../../../core/services/beneficiary.service';

@Component({
  selector: 'app-beneficiary-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficiary-list.html',
  styleUrl: './beneficiary-list.css'
})
export class BeneficiaryList implements OnInit {

  beneficiaries: any[] = [];
searchText = '';
  constructor(
  private beneficiaryService: BeneficiaryService,
  private cdr: ChangeDetectorRef,
  private router: Router
) {}

  ngOnInit(): void {
    this.loadBeneficiaries();
  }


  loadBeneficiaries(): void {

    const userId = Number(sessionStorage.getItem('userId'));

    console.log("Logged In User ID:", userId);

    this.beneficiaryService.getBeneficiaries(userId).subscribe({

      next: (data) => {

        console.log("Beneficiaries:", data);

        this.beneficiaries = data;

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);

      }

    });

  }
searchBeneficiary(): void {

  const userId = Number(sessionStorage.getItem('userId'));

  if (this.searchText.trim() === '') {
    this.loadBeneficiaries();
    return;
  }

  this.beneficiaryService
    .searchBeneficiary(userId, this.searchText)
    .subscribe({

      next: (data) => {

        this.beneficiaries = data;

        this.cdr.detectChanges();

      },

      error: (err) => {

        console.error(err);

      }

    });

}
clearSearch(): void {

  this.searchText = '';

  this.loadBeneficiaries();

}
  goToDashboard(): void {
    this.router.navigate(['/dashboard']);
}

  goToAddBeneficiary(): void {
  this.router.navigate(['/dashboard/add-beneficiary']);
}
editBeneficiary(id: number): void {

  this.router.navigate([
    '/dashboard/edit-beneficiary',
    id
  ]);

}
  deleteBeneficiary(id: number): void {

    if (confirm("Are you sure you want to delete this beneficiary?")) {

      this.beneficiaryService.deleteBeneficiary(id).subscribe({

        next: () => {

          alert("Beneficiary Deleted Successfully");

          this.loadBeneficiaries();

        },

        error: (err) => {

          console.error(err);

        }

      });

    }

  }

}