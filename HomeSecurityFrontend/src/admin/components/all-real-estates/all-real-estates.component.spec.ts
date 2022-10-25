import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRealEstatesComponent } from './all-real-estates.component';

describe('AllRealEstatesComponent', () => {
  let component: AllRealEstatesComponent;
  let fixture: ComponentFixture<AllRealEstatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllRealEstatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllRealEstatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
