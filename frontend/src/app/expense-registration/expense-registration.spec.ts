import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseRegistration } from './expense-registration';

describe('ExpenseRegistration', () => {
  let component: ExpenseRegistration;
  let fixture: ComponentFixture<ExpenseRegistration>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpenseRegistration]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpenseRegistration);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
