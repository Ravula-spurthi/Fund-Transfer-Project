import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Chatbot } from './components/chatbot/chatbot';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Chatbot],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
}