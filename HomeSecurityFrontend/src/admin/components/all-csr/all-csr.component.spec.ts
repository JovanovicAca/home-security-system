import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllCsrComponent } from './all-csr.component';

describe('AllCsrComponent', () => {
  let component: AllCsrComponent;
  let fixture: ComponentFixture<AllCsrComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllCsrComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllCsrComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
