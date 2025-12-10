import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardInvoice } from './card-invoice';

describe('CardInvoice', () => {
  let component: CardInvoice;
  let fixture: ComponentFixture<CardInvoice>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardInvoice]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardInvoice);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
