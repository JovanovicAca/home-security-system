import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewRealEstateModalComponent } from './new-real-estate-modal.component';

describe('NewRealEstateModalComponent', () => {
  let component: NewRealEstateModalComponent;
  let fixture: ComponentFixture<NewRealEstateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewRealEstateModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewRealEstateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
