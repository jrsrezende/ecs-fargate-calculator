import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../api';
import { ExpenseRequest } from '../models/expense-request';
import { CreditCard } from '../models/credit-card';

@Component({
  selector: 'app-expense-registration',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './expense-registration.html',
  styleUrl: './expense-registration.css',
})
export class ExpenseRegistration implements OnInit {
  expenseForm: FormGroup;
  cards: CreditCard[] = [];

  constructor(private fb: FormBuilder, private api: ApiService) {
    this.expenseForm = this.fb.group({
      creditCardId: ['', Validators.required],
      description: ['', Validators.required],
      amount: [null, [Validators.required, Validators.min(0)]],
      date: [new Date().toISOString().split('T')[0], Validators.required,] 
    });
  }

  ngOnInit(): void {
    this.api.getAllCards().subscribe({
      next: data => (this.cards = data),
      error: err => console.error(err)
    });
  }

  onSubmit(): void {
    if (this.expenseForm.invalid) {
      return;
    }

    const request: ExpenseRequest = this.expenseForm.value;

    this.api.createCharge(request).subscribe({
      next: (savedCharge) => {
        console.log('Credit card charge saved!', savedCharge);
        alert('Charge saved successfully!');
        this.expenseForm.reset({ 
          date: new Date().toISOString().split('T')[0]
        });
      },
      error: (err) => {
        console.error(err);
        alert('Error saving charge.');
      }
    });
  }
}
