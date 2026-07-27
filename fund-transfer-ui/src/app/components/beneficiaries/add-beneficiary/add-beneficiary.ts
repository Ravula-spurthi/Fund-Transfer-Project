import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficiaryService } from '../../../core/services/beneficiary.service';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-add-beneficiary',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-beneficiary.html',
  styleUrl: './add-beneficiary.css'
})
export class AddBeneficiary implements OnInit {

  beneficiary = {
    beneficiaryName: '',
    accountNumber: '',
    bankName: '',
    ifscCode: '',
    branch: '',
    mobileNumber: '',
    userId: Number(sessionStorage.getItem('userId')) || 1
  };

  isEdit = false;
  beneficiaryId!: number;

  constructor(
  private beneficiaryService: BeneficiaryService,
  private router: Router,
  private route: ActivatedRoute,
  private cdr: ChangeDetectorRef
) {}

  ngOnInit(): void {

  const id = this.route.snapshot.paramMap.get('id');

  console.log("Route ID:", id);

  if (id) {

    this.isEdit = true;
    this.beneficiaryId = Number(id);

    this.beneficiaryService.getBeneficiaryById(this.beneficiaryId)
      .subscribe({

        next: (data) => {

  this.beneficiary = { ...data };

  this.cdr.detectChanges();

},

        error: (err) => {

          console.log("Backend Error:", err);

        }

      });

  }

}

  goBack(): void {

    this.router.navigate([
      '/dashboard/beneficiary-list'
    ]);

  }

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

    const request = this.isEdit
      ? this.beneficiaryService.updateBeneficiary(
          this.beneficiaryId,
          this.beneficiary
        )
      : this.beneficiaryService.addBeneficiary(
          this.beneficiary
        );

    request.subscribe({

      next: () => {

        alert(
          this.isEdit
            ? 'Beneficiary Updated Successfully'
            : 'Beneficiary Added Successfully'
        );

        this.router.navigate([
          '/dashboard/beneficiary-list'
        ]);

      },

      error: (err) => {

        console.log(err);

        alert(err.error);

      }

    });

  }

}