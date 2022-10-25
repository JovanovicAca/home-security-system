import { Component, OnInit } from '@angular/core';
import { Csr } from 'src/app/models/Csr';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-all-csr',
  templateUrl: './all-csr.component.html',
  styleUrls: ['./all-csr.component.css']
})
export class AllCsrComponent implements OnInit {

  csrs: Csr[] = [];

  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.adminService.getPending().subscribe(
      (response) => {
        this.csrs = response;
      });
  }

  accept(id: number) {
    this.adminService.acceptCsr(id);
  }

  decline(id: number) {
    this.adminService.declineCsr(id);
  }

}
