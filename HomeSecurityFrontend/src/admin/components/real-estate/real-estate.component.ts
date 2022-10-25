import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { RealEstate } from 'src/app/models/RealEstate';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { EditOwnerModalComponent } from 'src/admin/modals/edit-owner-modal/edit-owner-modal.component';
import { EditTenantModalComponent } from 'src/admin/modals/edit-tenant-modal/edit-tenant-modal.component';

@Component({
  selector: 'app-real-estate',
  templateUrl: './real-estate.component.html',
  styleUrls: ['./real-estate.component.css'],
  providers: [MdbModalService]
})
export class RealEstateComponent implements OnInit {

  private routeSub: Subscription;
  realEstate: RealEstate;
  realEstateId: number;
  users: User[];
  modalRef: MdbModalRef<EditOwnerModalComponent>;

  constructor(
    private route: ActivatedRoute,
    private adminService: AdminService,
    private modalService: MdbModalService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      this.realEstateId = params['id'];
      });

    this.adminService.getUsersInRealEstate(this.realEstateId).subscribe(
      (response) => {
        this.users = response;
      });

    this.adminService.getRealEstate(this.realEstateId).subscribe(
      (response) => {
        this.realEstate = response;
      });
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }

  openEditOwnerModal() {
    this.adminService.getOwners().subscribe(
      (response) => {
        this.modalRef = this.modalService.open(EditOwnerModalComponent, {
          data: { 
            users: response,
            realEstate: this.realEstate
           },
        });
      });
  }

  openEditTenantModal() {
    this.adminService.getTenants().subscribe(
      (response) => {
        this.modalRef = this.modalService.open(EditTenantModalComponent, {
          data: { 
            users: response,
            realEstate: this.realEstate
           },
        });
      });
  }

  removeUserFromRealEstate(userId: number) {
    this.adminService.removeUserFromRealEstate(this.realEstate.id, userId);
    this.router.navigateByUrl('/RefreshComponent', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/admin/real-estate/' + this.realEstate.id]);
    });
  }
}
