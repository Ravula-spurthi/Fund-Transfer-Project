import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../core/services/dashboard.service';

import { Chart, ChartConfiguration, ChartType, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class Dashboard implements OnInit, AfterViewInit {

  @ViewChild('monthlyChart', { static: false }) monthlyChart?: ElementRef<HTMLCanvasElement>;
  monthlyChartInstance?: Chart;
  private chartReady = false;
  private pendingChartData: { labels: string[]; values: number[] } | null = null;

  dashboard: any = {};
  userName = sessionStorage.getItem('name') || 'Customer';
  showBalance = false;
  monthlyChartLoading = false;
  monthlyChartError = '';
  monthlyTransactions: any[] = [];

  public barChartType: ChartType = 'bar';
  public barChartData: ChartConfiguration<'bar'>['data'] = {
    labels: [],
    datasets: [
      {
        label: 'Monthly Transactions (₹)',
        data: [],
        backgroundColor: '#42A5F5'
      }
    ]
  };
  public barChartOptions: ChartConfiguration<'bar'>['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      x: {
        ticks: {
          autoSkip: false
        }
      },
      y: {
        beginAtZero: true
      }
    },
    plugins: {
      legend: { display: true }
    }
  };

  constructor(
    private router: Router,
    private dashboardService: DashboardService
  ) {}

  ngOnInit(): void {
    const userId = Number(sessionStorage.getItem('userId'));
    if (!userId) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadDashboard(userId);
    this.loadMonthlyTransactions(userId);
  }

  ngAfterViewInit(): void {
    this.chartReady = true;
    if (this.pendingChartData) {
      this.updateMonthlyChart(this.pendingChartData.labels, this.pendingChartData.values);
      this.pendingChartData = null;
    }
  }

  loadDashboard(userId: number): void {
    this.dashboardService.getDashboard(userId).subscribe({
      next: (data: any) => {
        this.dashboard = data || {};
      },
      error: (err) => {
        console.error('Dashboard load failed', err);
      }
    });
  }

  loadMonthlyTransactions(userId: number): void {
    this.monthlyChartLoading = true;
    this.monthlyChartError = '';
    this.monthlyTransactions = [];

    this.dashboardService.getMonthlyTransactions(userId).subscribe({
      next: (data: any[]) => {
        this.monthlyTransactions = data || [];

        const labels = this.monthlyTransactions.map(item =>
          item.month || item.label || item.period || item.monthName || item.yearMonth || 'N/A'
        );
        const values = this.monthlyTransactions.map(item =>
          Number(item.totalAmount ?? item.amount ?? item.transactionCount ?? item.totalTransactions ?? 0)
        );

        this.updateMonthlyChart(labels, values);
        this.monthlyChartLoading = false;
      },
      error: (err) => {
        console.error('Monthly transactions load failed', err);
        this.monthlyChartError = 'Unable to load monthly transaction data.';
        this.monthlyChartLoading = false;
      }
    });
  }

  viewBalance(): void {
    const pin = prompt('Enter Transaction PIN');
    if (!pin) {
      return;
    }

    const request = {
      userId: Number(sessionStorage.getItem('userId')),
      transactionPin: pin
    };

    this.dashboardService.getBalance(request).subscribe({
      next: (balance: number) => {
        this.dashboard.balance = balance;
        this.showBalance = true;
      },
      error: (err) => {
        console.error('Balance view failed', err);
        alert('Invalid PIN or unable to fetch balance.');
      }
    });
  }

  hideBalance(): void {
    this.showBalance = false;
  }

  private updateMonthlyChart(labels: string[], values: number[]): void {
    if (!this.chartReady || !this.monthlyChart?.nativeElement) {
      this.pendingChartData = { labels, values };
      return;
    }

    const context = this.monthlyChart.nativeElement.getContext('2d');
    if (!context) {
      this.pendingChartData = { labels, values };
      return;
    }

    if (this.monthlyChartInstance) {
      this.monthlyChartInstance.destroy();
    }

    const maxValue = values.length > 0 ? Math.max(...values) : 100;
    const magnitude = Math.pow(10, Math.floor(Math.log10(maxValue || 1)));
    const roundedMax = Math.ceil((maxValue || 1) / magnitude) * magnitude;
    const suggestedMax = Math.max(roundedMax, magnitude * 2);
    const stepSize = suggestedMax <= 1000 ? suggestedMax / 5 : suggestedMax / 10;

    this.monthlyChartInstance = new Chart(context, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Monthly Transactions (₹)',
            data: values,
            backgroundColor: '#42A5F5'
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            ticks: { autoSkip: false }
          },
          y: {
            beginAtZero: true,
            suggestedMax,
            ticks: {
              stepSize
            }
          }
        },
        plugins: {
          legend: { display: true }
        }
      }
    });
  }

  goToBeneficiary(): void {
    this.router.navigate(['/beneficiary-list']);
  }

  goToTransfer(): void {
    this.router.navigate(['/transfer-money']);
  }

  goToScheduledTransfer(): void {
    this.router.navigate(['/scheduled-transfer']);
  }

  goToTransactions(): void {
    this.router.navigate(['/transactions']);
  }

  goToStatement(): void {
    this.router.navigate(['/statement']);
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }

  goToSetPin(): void {
    this.router.navigate(['/set-pin']);
  }

  goToChangePin(): void {
    this.router.navigate(['/change-pin']);
  }

  logout(): void {
    sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}