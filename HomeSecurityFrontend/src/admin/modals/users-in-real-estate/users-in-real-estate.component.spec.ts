import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersInRealEstateComponent } from './users-in-real-estate.component';

describe('UsersInRealEstateComponent', () => {
  let component: UsersInRealEstateComponent;
  let fixture: ComponentFixture<UsersInRealEstateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsersInRealEstateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersInRealEstateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
