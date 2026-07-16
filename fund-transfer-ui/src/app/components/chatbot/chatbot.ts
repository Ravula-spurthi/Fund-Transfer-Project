import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chatbot',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './chatbot.html',
  styleUrl: './chatbot.css'
})
export class Chatbot {

  showChat = false;

  userMessage = '';

  messages: any[] = [
    {
      sender: 'bot',
      text: 'Hello! Welcome to Fund Transfer System. How can I help you today?'
    }
  ];

  toggleChat() {
    this.showChat = !this.showChat;
  }

  askSuggestion(question: string) {
    this.userMessage = question;
    this.sendMessage();
  }

  sendMessage() {

    if (!this.userMessage.trim()) {
      return;
    }

    this.messages.push({
      sender: 'user',
      text: this.userMessage
    });

    const msg = this.userMessage.toLowerCase();

    let reply = '';

    if (msg.includes('balance')) {
      reply = 'Go to Dashboard and click View Balance.';
    }
    else if (msg.includes('beneficiary')) {
      reply = 'Go to Add Beneficiary from Dashboard and enter beneficiary details.';
    }
    else if (msg.includes('transfer')) {
      reply = 'Open Fund Transfer, choose beneficiary, enter amount and Transaction PIN, then click Transfer.';
    }
    else if (msg.includes('statement')) {
      reply = 'Open Statement to view your account statement.';
    }
    else if (msg.includes('transaction')) {
      reply = 'Open Transactions from Dashboard to view transfer history.';
    }
    else if (msg.includes('pin')) {
      reply = 'You can Set or Change your Transaction PIN from the Dashboard.';
    }
    else if (msg.includes('profile')) {
      reply = 'Open Profile from Dashboard to view account information.';
    }
    else if (msg.includes('otp')) {
      reply = 'OTP is required for secure verification during transactions.';
    }
    else if (msg.includes('hello') || msg.includes('hi')) {
      reply = 'Hello! How can I help you today?';
    }
    else {
      reply = 'Sorry, I could not understand your question. Please ask about Balance, Transfer, Beneficiary, Statement, PIN or Transactions.';
    }

    setTimeout(() => {
      this.messages.push({
        sender: 'bot',
        text: reply
      });
    }, 500);

    this.userMessage = '';
  }

}