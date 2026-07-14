import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-profile-details',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    RouterModule
  ],
  templateUrl: './profile-details.html',
  styleUrl: './profile-details.css'
})
export class ProfileDetails implements OnInit {

  user: any = {};

  constructor(
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    const id = sessionStorage.getItem("userId");

    console.log("USER ID =", id);

    if (!id) {
      alert("User ID not found.");
      return;
    }

    this.http.get<any>("http://localhost:8080/api/users/" + id)
      .subscribe({

        next: (data) => {

          console.log("API DATA =", data);

          this.user = data;

          console.log("USER =", this.user);

          //alert(this.user.name);

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.log(err);

          alert("Failed to load profile");

        }

      });

  }

  goToSetPin() {
    this.router.navigate(['/set-pin']);
  }

  goToChangePin() {
    this.router.navigate(['/change-pin']);
  }

}