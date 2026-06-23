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
    userId: 1
  };

  constructor(
    private beneficiaryService: BeneficiaryService,
    private router: Router
  ) {}

  addBeneficiary() {

    this.beneficiaryService
      .addBeneficiary(this.beneficiary)
      .subscribe({

        next: () => {
          alert('Beneficiary Added Successfully');
          this.router.navigate(['/beneficiary-list']);
        },

        error: (err) => {
          console.log('ERROR =>', err);

           alert(
          'Status: ' +
           err.status +
          '\nMessage: ' +
           err.message
       );
      }
      });
  }
}