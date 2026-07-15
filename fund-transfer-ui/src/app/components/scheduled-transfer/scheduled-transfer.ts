import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScheduledTransferService } from '../../core/services/scheduled-transfer.service';

@Component({
  selector: 'app-scheduled-transfer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './scheduled-transfer.html',
  styleUrl: './scheduled-transfer.css'
})
export class ScheduledTransfer implements OnInit {

  scheduledTransfers: any[] = [];

  constructor(
    private scheduledTransferService: ScheduledTransferService
  ) {}

  ngOnInit(): void {
    this.loadTransfers();
  }

  loadTransfers() {
    this.scheduledTransferService.getAllTransfers()
      .subscribe({
        next: (data) => {
          console.log(data);
          this.scheduledTransfers = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  executeTransfer(id: number) {

    if (confirm("Execute this scheduled transfer?")) {

      this.scheduledTransferService.executeTransfer(id)
        .subscribe({
          next: () => {
            alert("Transfer Executed Successfully");
            this.loadTransfers();
          },
          error: (err) => {
            alert(err.error);
          }
        });

    }

  }

  deleteTransfer(id: number) {

    if (confirm("Delete this scheduled transfer?")) {

      this.scheduledTransferService.deleteTransfer(id)
        .subscribe({
          next: () => {
            alert("Scheduled Transfer Deleted");
            this.loadTransfers();
          },
          error: (err) => {
            alert(err.error);
          }
        });

    }

  }

}