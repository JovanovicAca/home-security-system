import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { RealEstate } from 'src/app/models/RealEstate';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-edit-tenant-modal',
  templateUrl: './edit-tenant-modal.component.html',
  styleUrls: ['./edit-tenant-modal.component.css']
})
export class EditTenantModalComponent {

  users: User[] = [];
  realEstate: RealEstate;
  public tenant = 1;

  constructor(
    public modalRef: MdbModalRef<EditTenantModalComponent>,
    private adminService: AdminService
  ) { }

  selectChangeHandler (event: any) {
    this.tenant = event.target.value;
  }

  saveChanges() {
    this.adminService.addRealEstateToUser(this.realEstate.id, this.tenant)
    window.location.reload();
  }

}
