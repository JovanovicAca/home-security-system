import { Component, OnInit } from '@angular/core';
import { RealEstate } from 'src/app/models/RealEstate';
import { AdminService } from 'src/app/services/admin.service';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { UsersInRealEstateComponent } from 'src/admin/modals/users-in-real-estate/users-in-real-estate.component';
import { NewRealEstateModalComponent } from 'src/admin/modals/new-real-estate-modal/new-real-estate-modal.component';

@Component({
  selector: 'app-all-real-estates',
  templateUrl: './all-real-estates.component.html',
  styleUrls: ['./all-real-estates.component.css'],
  providers: [MdbModalService]
})
export class AllRealEstatesComponent implements OnInit {

  realEstates: RealEstate[];
  modalRef: MdbModalRef<UsersInRealEstateComponent>;

  constructor(
    private adminService: AdminService, private modalService: MdbModalService
  ) { }

  ngOnInit(): void {
    this.adminService.getAllRealEstates().subscribe(
      (response) => {
        this.realEstates = response;
      });
  }

  openModal(realEstate: RealEstate) {
    this.adminService.getUsersInRealEstate(realEstate.id).subscribe(
      (response) => {
        this.modalRef = this.modalService.open(UsersInRealEstateComponent, {
          data: { 
            users: response,
            realEstate: realEstate
           },
        });
      });
  }

  openAddModal() {
    this.modalRef = this.modalService.open(NewRealEstateModalComponent);
  }

}
