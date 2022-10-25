import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Login } from 'src/app/models/Login';
import { Token } from 'src/app/models/Token';
import { AuthService } from 'src/app/services/auth.service';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  errorMessage = '';
  loginSubmits = 0;
  login!: Login;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastrService: ToastrService,
    private adminService: AdminService,

  ) {
    this.login = {
      username: '',
      password: ''
    }
  }

  onSubmit() {
    const username = (<HTMLInputElement>document.getElementById("username")).value;
    const password = (<HTMLInputElement>document.getElementById("password")).value;

    if (this.loginSubmits > 4) {
      this.adminService.lockAccount(username);
    } else {

      if (username === '' || password === '') {
        this.toastrService.error('Field empty!');
      } else {
        const auth: Login = {
          username: username,
          password: password,
        };

        this.authService.login(auth).subscribe({
          next: (result) => {
            localStorage.setItem('userToken', JSON.stringify(result));

            this.authService.setCurrentUser().subscribe(response => {
              localStorage.setItem("currentUser", JSON.stringify(response));

              const tokenString = localStorage.getItem('userToken');

              if (tokenString) {
                const token: Token = JSON.parse(tokenString);
                this.authService.setCurrentUser();

                const role = this.authService.getCurrentUser()?.role!.name;

                if (role === "ROLE_ADMIN") this.router.navigate(["admin/all-csr"]);
                if (role === "ROLE_OWNER") this.router.navigate(["owner/generator"]);
              }
            });
          },
          error: (error) => {
            if (error.status === 404) {
              this.toastrService.error('Bad credentials!');
              this.loginSubmits += 1;
            } else if (error.status == 400) {
              this.toastrService.error('Account locked!');
            }
          },
        });
      }
    }
  }

}
