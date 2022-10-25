import { Component } from '@angular/core';
import { CertificateExtension } from 'src/app/models/CertificateExtension';
import { NewCsr } from 'src/app/models/NewCsr';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-generate-certificate',
  templateUrl: './generate-certificate.component.html',
  styleUrls: ['./generate-certificate.component.css']
})
export class GenerateCertificateComponent {

  template: string = "CA";
  csr: NewCsr;

  constructor(
    private adminService: AdminService
  ) { }

  templateChange(value: string) {
    this.template = value;
 }

 submit() {
  
  var extensions: Array<CertificateExtension> = [];

  var elementAKI = <HTMLInputElement> document.getElementById("authorityKeyIdentifier");
  if (elementAKI.checked) {
    const extension: CertificateExtension = {
      name: "AKI"
    }
    extensions.push(extension);
  }

  var elementBC = <HTMLInputElement> document.getElementById("basicConstraints");
  if (elementBC.checked) {
    const extension: CertificateExtension = {
      name: "BC"
    }
    extensions.push(extension);
  }

  var elementKU = <HTMLInputElement> document.getElementById("keyUsage");
  if (elementKU.checked) {
    const extension: CertificateExtension = {
      name: "KU"
    }
    extensions.push(extension);
  }

  var elementSKI = <HTMLInputElement> document.getElementById("subjectKeyIdentifier");
  if (elementSKI.checked) {
    const extension: CertificateExtension = {
      name: "SKI"
    }
    extensions.push(extension);
  }

  var elementEKU = <HTMLInputElement> document.getElementById("extendedKeyUsage");
  if (elementEKU.checked) {
    const extension: CertificateExtension = {
      name: "EKU"
    }
    extensions.push(extension);
  }

  var elementSAN = <HTMLInputElement> document.getElementById("subjectAlternativeName");
  if (elementSAN.checked) {
    const extension: CertificateExtension = {
      name: "SAN"
    }
    extensions.push(extension);
  }

  const csr: NewCsr = {
    startDate: new Date((<HTMLInputElement>document.getElementById("startDate")).value),
    endDate: new Date ((<HTMLInputElement>document.getElementById("endDate")).value),
    commonName: (<HTMLInputElement>document.getElementById("commonName")).value,
    organizationUnit: (<HTMLInputElement>document.getElementById("organizationUnit")).value,
    organizationName: (<HTMLInputElement>document.getElementById("organizationName")).value,
    localityName: (<HTMLInputElement>document.getElementById("localityName")).value,
    stateName: (<HTMLInputElement>document.getElementById("stateName")).value,
    country: (<HTMLInputElement>document.getElementById("country")).value,
    email: (<HTMLInputElement>document.getElementById("email")).value,
    extensions
  };

  this.csr = csr;
  this.adminService.generate(this.csr);
}

}
