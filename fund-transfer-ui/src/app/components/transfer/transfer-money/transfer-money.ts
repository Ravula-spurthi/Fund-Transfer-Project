import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TransferService } from '../../../core/services/transfer';

@Component({
  selector: 'app-transfer-money',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './transfer-money.html',
  styleUrl: './transfer-money.css'
})
export class TransferMoney {

  transferData = {
  beneficiaryName: '',
  amount: 0
};

  constructor(private transferService: TransferService) {}

  transfer() {

  this.transferService
      .transferMoney(this.transferData)
      .subscribe({

        next: (response) => {
          alert(response);
        },

        error: (error) => {
  console.log('FULL ERROR:', error);
  alert(JSON.stringify(error));
      }

      });
}

  resetForm() {
  this.transferData = {
    beneficiaryName: '',
    amount: 0
  };
}
}