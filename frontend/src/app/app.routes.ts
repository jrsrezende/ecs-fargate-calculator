import { Routes } from '@angular/router';
import { CardRegistration } from './card-registration/card-registration';
import { ExpenseRegistration } from './expense-registration/expense-registration';
import { CardInvoice } from './card-invoice/card-invoice';

export const routes: Routes = [
  { path: '', redirectTo: 'invoice', pathMatch: 'full' },
  { path: 'invoice', component: CardInvoice },
  { path: 'new-card', component: CardRegistration },
  { path: 'new-expense', component: ExpenseRegistration },
];
