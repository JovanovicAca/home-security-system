import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';
import { AuthService } from 'src/app/services/auth.service';
import { NewUserModalComponent } from 'src/admin/modals/new-user-modal/new-user-modal/new-user-modal.component';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { EditRoleModalComponent } from 'src/admin/modals/edit-role-modal/edit-role-modal/edit-role-modal.component';

import { UserRealEstatesComponent } from 'src/admin/modals/user-real-estates/user-real-estates.component';


@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css'],
  providers: [MdbModalService]
})
export class AllUsersComponent implements OnInit {

  users: User[] = [];
  currentUser: string | undefined;
  modalRef: MdbModalRef<NewUserModalComponent>;

  constructor(
    private adminService: AdminService, private authService: AuthService, private modalService: MdbModalService,
  ) { }

  ngOnInit(): void {
    this.adminService.getUsers().subscribe(
      (response) => {
        this.users = response;
      });

    this.currentUser = this.authService.getCurrentUser()?.username;
  }

  delete(username: string) {
    this.adminService.deleteUser(username);
  }

  openCreateModal() {
    this.modalRef = this.modalService.open(NewUserModalComponent);
  }

  openEditRoleModal(user: User) {
    this.modalRef = this.modalService.open(EditRoleModalComponent, {
      data: { user: user },
    });
  }

  userRealEstates(id: number) {
    this.adminService.getUserRealEstates(id).subscribe(
      (response) => {
        this.modalRef = this.modalService.open(UserRealEstatesComponent, {
          data: { realEstates: response },
        });
      });
  }
}

