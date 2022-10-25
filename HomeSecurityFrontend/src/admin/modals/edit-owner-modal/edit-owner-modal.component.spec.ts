import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditOwnerModalComponent } from './edit-owner-modal.component';

describe('EditOwnerModalComponent', () => {
  let component: EditOwnerModalComponent;
  let fixture: ComponentFixture<EditOwnerModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditOwnerModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditOwnerModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
