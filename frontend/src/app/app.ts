import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CardRegistration } from './card-registration/card-registration';
import { ExpenseRegistration } from './expense-registration/expense-registration';
import { InvoiceSummary } from './invoice-summary/invoice-summary';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, CardRegistration, ExpenseRegistration, InvoiceSummary],
  template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
      <div class="container-fluid">
        <a class="navbar-brand"  href="#" (click)="navigateTo('summary', $event)"> Invoice Summary </a>
        <button class="navbar-toggler" type="button" 
            data-bs-toggle="collapse" 
            data-bs-target="#navbarNav" 
            aria-controls="navbarNav" 
            aria-expanded="false" 
            aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" [class.active]="currentPage === 'new-card'" href="#" (click)="navigateTo('new-card', $event)">Register New Card</a>
            </li>
            <li li class="nav-item">
              <a class="nav-link" [class.active]="currentPage === 'new-expense'" href="#" (click)="navigateTo('new-expense', $event)"> Register Expense </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <main class="container mt-5">
      <div [ngSwitch]="currentPage">
        <app-card-registration *ngSwitchCase="'new-card'"></app-card-registration>
        <app-expense-registration *ngSwitchCase="'new-expense'"></app-expense-registration>
        <app-invoice-summary *ngSwitchCase="'summary'"></app-invoice-summary>
       </div>
    </main>
  `
})
export class App {
  currentPage = 'summary';

  navigateTo(page: string, event: MouseEvent): void {
    event.preventDefault();
    this.currentPage = page;
  }
}