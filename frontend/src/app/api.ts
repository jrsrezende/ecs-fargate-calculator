import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { CreditCard } from './models/credit-card';
import { CreditCardRequest } from './models/credit-card-request';
import { ExpenseRequest } from './models/expense-request';
import { Expense } from './models/expense';
import { InvoiceResponse } from './models/invoice-response';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private readonly BASE_URL = '/api';

  constructor(private http: HttpClient) { }

  public createCard(request: CreditCardRequest): Observable<CreditCard> {
    return this.http.post<CreditCard>(`${this.BASE_URL}/credit-cards`, request);
  }

  public getAllCards(): Observable<CreditCard[]> {
    return this.http.get<CreditCard[]>(`${this.BASE_URL}/credit-cards`);
  }

  public getCardInvoice(cardId: string, date: Date): Observable<InvoiceResponse> {
    const month = date.getMonth() + 1;
    const year = date.getFullYear();
    const params = new HttpParams().set('month', month.toString()).set('year', year.toString());
    return this.http.get<InvoiceResponse>(`${this.BASE_URL}/credit-cards/${cardId}/invoice`, {params: params});
  }

  public createCharge(request: ExpenseRequest): Observable<Expense> {
    return this.http.post<Expense>(`${this.BASE_URL}/expenses`, request);
  }
}