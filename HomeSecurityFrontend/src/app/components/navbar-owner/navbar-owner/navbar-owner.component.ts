import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-owner',
  templateUrl: './navbar-owner.component.html',
  styleUrls: ['./navbar-owner.component.css']
})
export class NavbarOwnerComponent {

  constructor(
    private authService : AuthService, private router: Router
  ) { }

  logout(){
    this.authService.logout();
    this.router.navigate(["login"]);
  }

}
