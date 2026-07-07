import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransferService } from '../../../core/services/transfer';

@Component({
  selector: 'app-transfer-money',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './transfer-money.html',
  styleUrl: './transfer-money.css'
})
export class TransferMoney implements OnInit {

  beneficiaries: any[] = [];

  transferData = {

    fromAccount: sessionStorage.getItem("accountNumber") || "123456780",

    availableBalance: sessionStorage.getItem("balance") || "42000",

    beneficiaryName: "",

    accountNumber: "",

    ifscCode: "",

    bankName: "",

    branch: "",

    mobileNumber: "",

    amount: 0,

    transferDate: new Date().toISOString().substring(0, 10),

    remarks: "",

    paymentType: "Pay Now",

    scheduleDate: ""

  };

  constructor(private transferService: TransferService) {}

  ngOnInit(): void {

    this.transferService.getBeneficiaries().subscribe({

      next: (data: any[]) => {

        console.log("Beneficiaries Loaded:", data);

        this.beneficiaries = data;

      },

      error: (err) => {

        console.error("Error loading beneficiaries:", err);

        alert("Failed to load beneficiaries");

      }

    });

  }

  onBeneficiaryChange(): void {

    const selected = this.beneficiaries.find(
      b => b.beneficiaryName === this.transferData.beneficiaryName
    );

    if (selected) {

      this.transferData.accountNumber = selected.accountNumber;
      this.transferData.ifscCode = selected.ifscCode;
      this.transferData.bankName = selected.bankName;
      this.transferData.branch = selected.branch;
      this.transferData.mobileNumber = selected.mobileNumber;

    }

  }

  transfer(): void {

    const request = {

      senderAccount: this.transferData.fromAccount,

      receiverAccount: this.transferData.accountNumber,

      beneficiaryName: this.transferData.beneficiaryName,

      amount: this.transferData.amount,

      remarks: this.transferData.remarks,

      paymentType: this.transferData.paymentType,

      scheduleDate: this.transferData.scheduleDate

    };

    console.log("Transfer Request:", request);

    this.transferService.transferMoney(request).subscribe({

      next: (response: any) => {

        alert(response);

        this.resetForm();

      },

      error: (error) => {

        console.error(error);

        alert("Transfer Failed");

      }

    });

  }

  resetForm(): void {

    this.transferData.beneficiaryName = "";
    this.transferData.accountNumber = "";
    this.transferData.ifscCode = "";
    this.transferData.bankName = "";
    this.transferData.branch = "";
    this.transferData.mobileNumber = "";
    this.transferData.amount = 0;
    this.transferData.transferDate = new Date().toISOString().substring(0, 10);
    this.transferData.remarks = "";
    this.transferData.paymentType = "Pay Now";
    this.transferData.scheduleDate = "";

  }

}