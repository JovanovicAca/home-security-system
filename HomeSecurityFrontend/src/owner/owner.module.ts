import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { GenerateCertificateComponent } from 'src/owner/components/generate-certificate/generate-certificate.component';
import { OwnerRoutes } from './owner.routes';

@NgModule({
  declarations: [
    GenerateCertificateComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    RouterModule.forChild(OwnerRoutes)
  ]
})
export class OwnerModule {}