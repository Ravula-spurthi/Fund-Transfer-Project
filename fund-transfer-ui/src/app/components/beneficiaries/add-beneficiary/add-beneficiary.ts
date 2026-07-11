import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { BeneficiaryService } from '../../../core/services/beneficiary.service';

@Component({
  selector: 'app-add-beneficiary',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-beneficiary.html',
  styleUrl: './add-beneficiary.css'
})
export class AddBeneficiary {

  beneficiary = {
    beneficiaryName: '',
    accountNumber: '',
    bankName: '',
    ifscCode: '',
    branch: '',
    mobileNumber: '',
    userId: Number(sessionStorage.getItem('userId')) || 1
  };

  constructor(
    private beneficiaryService: BeneficiaryService,
    private router: Router
  ) {}

  addBeneficiary() {

    if (
      !this.beneficiary.beneficiaryName ||
      !this.beneficiary.accountNumber ||
      !this.beneficiary.bankName ||
      !this.beneficiary.ifscCode ||
      !this.beneficiary.branch ||
      !this.beneficiary.mobileNumber
    ) {
      alert('Please fill all fields');
      return;
    }

    this.beneficiaryService
      .addBeneficiary(this.beneficiary)
      .subscribe({

        next: () => {

          alert('Beneficiary Added Successfully');

          this.router.navigate(['/beneficiary-list']);

        },

        error: (err) => {

          console.log(err);

          alert(err.error);

        }

      });

  }

}