import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-profile-details',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,RouterModule
  ],
  templateUrl: './profile-details.html',
  styleUrl: './profile-details.css'
})
export class ProfileDetails implements OnInit {

  user:any={};

  constructor(private http:HttpClient, private router: Router){}

  ngOnInit() {

  const id = sessionStorage.getItem("userId");
  console.log("USER ID =", id);
  if (!id) {
    console.log("User ID not found in session storage.");
    return;
  }
  this.http.get<any>("http://localhost:8080/api/users/" + id)
    .subscribe({

      next: (data) => {
        console.log("API DATA =", data);
        this.user = data;
        console.log("USER =", this.user);
      },

      error: (err) => {
        console.log(err);
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