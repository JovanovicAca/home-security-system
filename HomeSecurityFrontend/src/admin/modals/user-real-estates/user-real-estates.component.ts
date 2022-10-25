import { Component } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { RealEstate } from 'src/app/models/RealEstate';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-user-real-estates',
  templateUrl: 'user-real-estates.component.html',
  styleUrls: ['user-real-estates.component.css']
})
export class UserRealEstatesComponent {

  realEstates: RealEstate[];

  constructor(
    public modalRef: MdbModalRef<UserRealEstatesComponent>, 
    private adminService: AdminService
  ) { }

  onChange(value: any) {
    for (let i = 0; i < this.realEstates.length; i++) {
      if (this.realEstates[i].id == value.id) {
        this.realEstates[i].access = value.checked;
        this.adminService.editAccessToRealEstate(this.realEstates[i]);
      }
    }
  }

}
