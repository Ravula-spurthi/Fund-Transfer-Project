import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

import { BeneficiaryService } from '../../../core/services/beneficiary.service';

@Component({
  selector: 'app-beneficiary-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './beneficiary-list.html',
  styleUrl: './beneficiary-list.css'
})
export class BeneficiaryList implements OnInit {

  beneficiaries: any[] = [];

  constructor(
    private beneficiaryService: BeneficiaryService,
    private cdr: ChangeDetectorRef
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