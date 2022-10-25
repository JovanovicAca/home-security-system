import { Routes } from "@angular/router";
import { RoleGuard } from "../app/guards/role.guard";
import { GenerateCertificateComponent } from "src/owner/components/generate-certificate/generate-certificate.component";

export const OwnerRoutes: Routes = [
  {
    path: "generator",
    pathMatch: "full",
    component: GenerateCertificateComponent,
    canActivate: [RoleGuard],
    data: { expectedRoles: "ROLE_OWNER" },
  }
];