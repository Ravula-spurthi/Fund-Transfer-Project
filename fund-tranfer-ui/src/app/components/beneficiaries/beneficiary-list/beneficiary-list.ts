import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-beneficiary-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './beneficiary-list.html',
  styleUrl: './beneficiary-list.css'
})
export class BeneficiaryList {

  constructor(private router: Router) {}

  goToTransfer() {
    this.router.navigate(['/transfer-money']);
  }

}