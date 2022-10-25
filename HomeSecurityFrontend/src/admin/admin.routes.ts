import { Routes } from "@angular/router";
import { RoleGuard } from "../app/guards/role.guard";
import { AllCertificatesComponent } from "./components/all-certificates/all-certificates.component";
import { AllCsrComponent } from "./components/all-csr/all-csr.component";
import { AllUsersComponent } from "src/admin/components/all-users/all-users.component";
import { AllRealEstatesComponent } from "./components/all-real-estates/all-real-estates.component";
import { RealEstateComponent } from "./components/real-estate/real-estate.component";

export const AdminRoutes: Routes = [
  {
    path: "all-csr",
    pathMatch: "full",
    component: AllCsrComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "all-certificates",
    pathMatch: "full",
    component: AllCertificatesComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "all-users",
    pathMatch: "full",
    component: AllUsersComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: "all-real-estates",
    pathMatch: "full",
    component: AllRealEstatesComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  },
  {
    path: 'real-estate/:id',
    pathMatch: "full",
    component: RealEstateComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_ADMIN" },
  }
];