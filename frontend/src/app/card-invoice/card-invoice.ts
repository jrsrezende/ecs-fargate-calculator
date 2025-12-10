import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api';
import { CreditCard } from '../models/credit-card';
import { InvoiceResponse } from '../models/invoice-response';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card-invoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card-invoice.html',
  styleUrl: './card-invoice.css',
})
export class CardInvoice implements OnInit {

  cards: CreditCard[] = [];
  selectedInvoice: InvoiceResponse | null = null;
  isLoading = false;
  selectedCardId: string | null = null;
  currentDisplayDate: Date = new Date();

  constructor(private api: ApiService) { }

  ngOnInit(): void {
    this.api.getAllCards().subscribe({
      next: (data) => {
        this.cards = data;
      },
      error: (err) => console.error(err)
    });
  }

  onCardSelected(event: Event): void {
    const cardId = (event.target as HTMLSelectElement).value;
    this.selectedCardId = cardId;

    if (!this.selectedCardId) {
      this.selectedInvoice = null;
      return;
    }

    const selectedCard = this.cards.find(c => c.cardId === cardId);
    
    if (selectedCard) {
      this.setInvoiceDateBasedOnClosingDay(selectedCard);
    }
    
    this.fetchInvoice();
  }

  private setInvoiceDateBasedOnClosingDay(card: CreditCard): void {
    const today = new Date();
    const currentDay = today.getDate();
    
    if (card.closingDay && currentDay >= card.closingDay) {
      today.setMonth(today.getMonth() + 1);
    }

    this.currentDisplayDate = today;
  }

  fetchInvoice(): void {
    if (!this.selectedCardId) {
      return; 
    }
    this.isLoading = true;
    this.selectedInvoice = null;
    this.api.getCardInvoice(this.selectedCardId, this.currentDisplayDate).subscribe({
      next: (invoice) => {
        this.selectedInvoice = invoice;
        this.isLoading = false;
      },
      error: (err) => {
        console.error(err);
        alert('Error searching for invoice');
        this.isLoading = false;
      }
    });
  }

  changeMonth(offset: number): void {
    const newDate = new Date(this.currentDisplayDate.getTime());
    newDate.setMonth(newDate.getMonth() + offset);
    this.currentDisplayDate = newDate;
    this.fetchInvoice();
  }
} {

}
