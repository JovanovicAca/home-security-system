import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTenantModalComponent } from './edit-tenant-modal.component';

describe('EditTenantModalComponent', () => {
  let component: EditTenantModalComponent;
  let fixture: ComponentFixture<EditTenantModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTenantModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditTenantModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
