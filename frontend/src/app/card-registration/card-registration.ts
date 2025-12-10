import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../api';
import { CreditCardRequest } from '../models/credit-card-request';

@Component({
  selector: 'app-card-registration',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './card-registration.html',
  styleUrl: './card-registration.css',
})
export class CardRegistration {
  cardForm: FormGroup;

  constructor(private fb: FormBuilder, private api: ApiService) {
    this.cardForm = this.fb.group({
      name: ['', Validators.required],
      closingDay: [null, [Validators.required, Validators.min(1), Validators.max(31)]],
      dueDay: [null, [Validators.required, Validators.min(1), Validators.max(31)]],
    });
  }

  onSubmit(): void {
    if (this.cardForm.invalid) {
      return; 
    }

    const request: CreditCardRequest = this.cardForm.value;

    this.api.createCard(request).subscribe({
      next: (savedCard) => {
        console.log('Card saved!', savedCard);
        alert('Card saved successfully!');
        this.cardForm.reset();
      },
      error: (err) => {
        console.error(err);
        alert('Error saving card.');
      }
    });
  }
}
