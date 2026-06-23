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

  loadBeneficiaries() {
    this.beneficiaryService.getBeneficiaries()
      .subscribe(data => {
        this.beneficiaries = data;
      });
  }

  deleteBeneficiary(id: number) {
    this.beneficiaryService.deleteBeneficiary(id)
      .subscribe(() => {
        alert('Beneficiary Deleted');
        this.loadBeneficiaries();
      });
  }
}