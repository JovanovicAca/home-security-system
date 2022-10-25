import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { RealEstate } from 'src/app/models/RealEstate';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-in-real-estate',
  templateUrl: './users-in-real-estate.component.html',
  styleUrls: ['./users-in-real-estate.component.css']
})
export class UsersInRealEstateComponent {

  users: User[];
  realEstate: RealEstate;

  constructor(
    public modalRef: MdbModalRef<UsersInRealEstateComponent>,
    private router: Router
  ) { }

  navigateToRealEstate() {
    document.getElementById("closeModalButton")!.click();
    this.router.navigateByUrl('admin/real-estate/' + this.realEstate.id);
  }

}
