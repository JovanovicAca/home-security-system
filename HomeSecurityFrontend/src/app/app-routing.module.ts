import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { LoginGuard } from './guards/login.guard';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { BaseLayoutComponent } from './pages/base-layout/base-layout.component';

const routes: Routes = [
  {
    path:"login",
    component: LoginComponent
  },
  {
    path:"",
    component: BaseLayoutComponent,
    canActivate: [LoginGuard],
    children:[
      {
        path: "admin",
        loadChildren: () =>
          import("../admin/admin.module").then((m) => m.AdminModule)
      },
      {
        path: "owner",
        loadChildren: () =>
          import("../owner/owner.module").then((m) => m.OwnerModule)
      }
    ]
  },
  {
    path: "**",
    component: NotFoundComponent,
  }
];

@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
