import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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

  constructor(private beneficiaryService: BeneficiaryService) {}

  ngOnInit(): void {
    this.loadBeneficiaries();
  }

  loadBeneficiaries(): void {
    this.beneficiaryService.getBeneficiaries().subscribe({
      next: (data) => {
        console.log("beneficiaries =", data);
        this.beneficiaries = data;
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