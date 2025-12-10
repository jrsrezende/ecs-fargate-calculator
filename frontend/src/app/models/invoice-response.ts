import { Expense } from './expense';

export interface InvoiceResponse {
  totalAmount: number;
  invoiceStartDate: string; 
  invoiceEndDate: string;
  dueDate: string;
  cardName: string;
  expenses: Expense[];
}