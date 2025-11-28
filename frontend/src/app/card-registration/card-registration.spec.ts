import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardRegistration } from './card-registration';

describe('CardRegistration', () => {
  let component: CardRegistration;
  let fixture: ComponentFixture<CardRegistration>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardRegistration]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CardRegistration);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
