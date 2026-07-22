import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScheduledTransferService } from '../../core/services/scheduled-transfer.service';

@Component({
  selector: 'app-scheduled-transfer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './scheduled-transfer.html',
  styleUrls: ['./scheduled-transfer.css']
})
export class ScheduledTransfer implements OnInit {

  scheduledTransfers: any[] = [];

  constructor(
    private scheduledTransferService: ScheduledTransferService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    console.log("Scheduled Transfer Component Loaded");
    this.loadTransfers();
  }

 loadTransfers(): void {

  const userId = Number(sessionStorage.getItem('userId'));

  console.log("Logged In User:", userId);

  this.scheduledTransferService.getAllTransfers(userId).subscribe({

    next: (data: any[]) => {
      console.log(data);
      this.scheduledTransfers = data;
      this.cdr.detectChanges();
    },

    error: (err: any) => {
      console.error(err);
    }

  });

}
  executeTransfer(id: number): void {

    if (confirm("Execute this scheduled transfer?")) {

      this.scheduledTransferService.executeTransfer(id)
        .subscribe({

          next: () => {

            alert("Transfer Executed Successfully");

            this.loadTransfers();

          },

          error: (err) => {

            console.error(err);

            alert("Unable to execute transfer");

          }

        });

    }

  }

  deleteTransfer(id: number): void {

    if (confirm("Delete this scheduled transfer?")) {

      this.scheduledTransferService.deleteTransfer(id)
        .subscribe({

          next: () => {

            alert("Scheduled Transfer Deleted Successfully");

            this.loadTransfers();

          },

          error: (err) => {

            console.error(err);

            alert("Unable to delete transfer");

          }

        });

    }

  }

}