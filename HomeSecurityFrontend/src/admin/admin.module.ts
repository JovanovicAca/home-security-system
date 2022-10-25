import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AdminRoutes } from './admin.routes';
import { AllCertificatesComponent } from './components/all-certificates/all-certificates.component';
import { AllCsrComponent } from './components/all-csr/all-csr.component';
import { AllUsersComponent } from './components/all-users/all-users.component';
import { NewUserModalComponent } from './modals/new-user-modal/new-user-modal/new-user-modal.component';
import { EditRoleModalComponent } from './modals/edit-role-modal/edit-role-modal/edit-role-modal.component';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table'
import { UserRealEstatesComponent } from './modals/user-real-estates/user-real-estates.component';
import { AllRealEstatesComponent } from './components/all-real-estates/all-real-estates.component';
import { UsersInRealEstateComponent } from './modals/users-in-real-estate/users-in-real-estate.component';
import { RealEstateComponent } from './components/real-estate/real-estate.component';
import { NewRealEstateModalComponent } from './modals/new-real-estate-modal/new-real-estate-modal.component';
import { EditOwnerModalComponent } from './modals/edit-owner-modal/edit-owner-modal.component';
import { EditTenantModalComponent } from './modals/edit-tenant-modal/edit-tenant-modal.component';

@NgModule({
  declarations: [
    AllCertificatesComponent,
    AllCsrComponent,
    AllUsersComponent,
    NewUserModalComponent,
    EditRoleModalComponent,
    UserRealEstatesComponent,
    AllRealEstatesComponent,
    UsersInRealEstateComponent,
    RealEstateComponent,
    NewRealEstateModalComponent,
    EditOwnerModalComponent,
    EditTenantModalComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    RouterModule.forChild(AdminRoutes),
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule
  ],
  providers: [],
  entryComponents: [
    NewUserModalComponent,
    UsersInRealEstateComponent,
    EditOwnerModalComponent
  ]
})
export class AdminModule { }