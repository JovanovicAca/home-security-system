import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.css']
})
export class BaseLayoutComponent implements OnInit {
  currentRole : any

  constructor(
    private authService : AuthService
  ) { }

  ngOnInit(): void {
    this.currentRole = this.authService.getCurrentUser()?.role.name;
  }

}
