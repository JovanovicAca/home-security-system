import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { ToastrService } from 'ngx-toastr';
import { NewUser } from 'src/app/models/NewUser';
import { User } from 'src/app/models/User';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-edit-role-modal',
  templateUrl: './edit-role-modal.component.html',
  styleUrls: ['./edit-role-modal.component.css']
})
export class EditRoleModalComponent {

  user: User;

  constructor(
    public modalRef: MdbModalRef<EditRoleModalComponent>, 
    private adminService: AdminService,
    private toastrService: ToastrService
  ) { }

  saveUser() {
    const role = (<HTMLInputElement>document.getElementById('role')).value;
    const newUser = new NewUser(
      this.user.firstName, this.user.lastName, this.user.email, this.user.username, this.user.password, 
      (<HTMLInputElement>document.getElementById('role')).value
    );
    if (role === 'admin' || role === 'owner' || role === 'tenant') {
      this.adminService.updateUser(newUser);
      window.location.reload();
    } else {
      this.toastrService.error('Role has to be "admin" or "owner" or "tenant"!');
    }
  }

}
