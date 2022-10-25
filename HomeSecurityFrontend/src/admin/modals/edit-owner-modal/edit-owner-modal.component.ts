import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { RealEstate } from 'src/app/models/RealEstate';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-edit-owner-modal',
  templateUrl: './edit-owner-modal.component.html',
  styleUrls: ['./edit-owner-modal.component.css']
})
export class EditOwnerModalComponent {

  users: User[] = [];
  realEstate: RealEstate;
  public owner = 1;

  constructor(
    public modalRef: MdbModalRef<EditOwnerModalComponent>,
    private adminService: AdminService
  ) { }

  selectChangeHandler (event: any) {
    this.owner = event.target.value;
  }

  saveChanges() {
    this.adminService.addRealEstateToUser(this.realEstate.id, this.owner)
    window.location.reload();
  }

}
