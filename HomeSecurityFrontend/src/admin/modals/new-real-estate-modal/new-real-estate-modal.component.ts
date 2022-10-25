import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { AdminService } from 'src/app/services/admin.service';
import { NewRealEstate } from 'src/app/models/NewRealEstate';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-new-real-estate-modal',
  templateUrl: './new-real-estate-modal.component.html',
  styleUrls: ['./new-real-estate-modal.component.css']
})
export class NewRealEstateModalComponent implements OnInit {

  newRealEstate!: NewRealEstate;
  users: User[] = [];

  constructor(
    public modalRef: MdbModalRef<NewRealEstateModalComponent>,
    private adminService: AdminService
  ) {
    this.newRealEstate = {
      name: '',
      ownerId: 1
    }
   }

   public name = '';
   public owner = 1;

  ngOnInit(): void {
    this.adminService.getOwners().subscribe(
      (response) => {
        this.users = response;
        this.owner = this.users[0].id;
      });

  }

  saveRealEstate() {
    this.name = (<HTMLInputElement>document.getElementById('name')).value;
    this.newRealEstate = new NewRealEstate(this.name, this.owner);
    this.adminService.createRealEstate(this.newRealEstate);
    window.location.reload();
  }

  selectChangeHandler (event: any) {
    this.owner = event.target.value;
  }

}
