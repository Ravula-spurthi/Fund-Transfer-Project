import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-profile-details',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule
  ],
  templateUrl: './profile-details.html',
  styleUrl: './profile-details.css'
})
export class ProfileDetails implements OnInit {

  user:any={};

  constructor(private http:HttpClient){}

  ngOnInit(){

    const id=sessionStorage.getItem("userId");

    this.http.get(
      "http://localhost:8080/api/users/"+id
    ).subscribe(data=>{

      this.user=data;

    });

  }

}