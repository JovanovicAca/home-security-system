import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { Certificate } from 'src/app/models/Certificate';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {

  certificates: Certificate[] = [];
  pipe = new DatePipe('en-US');

  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.adminService.getCertificates().subscribe(
      (response) => {
        this.certificates = response;
        this.certificates.forEach(cer => {
          cer.endDate = new Date(cer.endDate)
        })
        console.log(this.certificates);

      });
  }

  revoke(serialNum: number, reason: string, email: string) {
    // const reason = (<HTMLInputElement>document.getElementById("reasonRevoke")).value
    this.adminService.revoke(serialNum, reason, email);
  }

}
