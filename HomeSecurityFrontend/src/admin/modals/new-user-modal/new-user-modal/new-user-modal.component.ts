import { Component } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';
import { AdminService } from 'src/app/services/admin.service';
import { ToastrService } from 'ngx-toastr';
import { NewUser } from 'src/app/models/NewUser';

@Component({
  selector: 'app-new-user-modal',
  templateUrl: './new-user-modal.component.html',
  styleUrls: ['./new-user-modal.component.css']
})
export class NewUserModalComponent {

  newUser!: NewUser;

  constructor(
    public modalRef: MdbModalRef<NewUserModalComponent>,
    private adminService: AdminService,
    private toastrService: ToastrService
  ) {
    this.newUser = {
      firstName: '',
      lastName: '',
      email: '',
      username: '',
      password: '',
      role: '',

    }
  }

  public username = '';
  public role = '';
  public lastName = '';
  public emailAddress = '';
  public password = '';
  public password2 = '';
  public name = '';

  saveUser() {
    // this.name = (<HTMLInputElement>document.getElementById('name')).value
    // this.lastName = (<HTMLInputElement>document.getElementById('lastName')).value;
    this.emailAddress = (<HTMLInputElement>document.getElementById('emailAddress')).value;
    // this.username = (<HTMLInputElement>document.getElementById('username')).value;
    this.password = (<HTMLInputElement>document.getElementById('password')).value;
    this.password2 = (<HTMLInputElement>document.getElementById('password2')).value;
    // this.role = (<HTMLInputElement>document.getElementById('role')).value;
    // const user = new NewUser(
    //   (<HTMLInputElement>document.getElementById('name')).value,
    //   (<HTMLInputElement>document.getElementById('lastName')).value,
    //   (<HTMLInputElement>document.getElementById('emailAddress')).value,
    //   (<HTMLInputElement>document.getElementById('username')).value,
    //   (<HTMLInputElement>document.getElementById('password')).value,
    //   (<HTMLInputElement>document.getElementById('role')).value
    // );
    if (this.password == this.password2) {
      if (this.newUser.role === 'admin' || this.newUser.role === 'owner' || this.newUser.role === 'tenant') {

        const specialChars = /[`!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
        const upperCase = /[A-Z]/;
        const lowerCase = /[a-z]/;
        // if (!this.validateEmail(this.emailAddress)) {

        //   return;
        // }
        if (this.password.length < 8 || !specialChars.test(this.password) || !upperCase.test(this.password)
          || !lowerCase.test(this.password)) {

          this.toastrService.error('Password: min 8 letters, special character, upper case, lower case!');

        }
        else if (!this.validateEmail(this.emailAddress)) {
          this.toastrService.error('Email: email must be in right form!');
        }
        else {
          this.adminService.createUser(this.newUser as NewUser);
          window.location.reload();

        }

      } else {
        this.toastrService.error('Role has to be "admin" or "owner" or "tenant"!');
      }
    } else {
      this.toastrService.error('Passwords do not match!');
    }
  }
  validateEmail = (email: string) => {
    return String(email)
      .toLowerCase()
      .match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      );
  };

}