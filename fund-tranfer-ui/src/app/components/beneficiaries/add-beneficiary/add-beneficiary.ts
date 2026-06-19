import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-add-beneficiary',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './add-beneficiary.html',
  styleUrl: './add-beneficiary.css'
})
export class AddBeneficiary {

  constructor(private router: Router) {}

  addBeneficiary() {
    alert('Beneficiary Added Successfully');
    this.router.navigate(['/beneficiary-list']);
  }

}