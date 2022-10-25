import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { OverlayModule } from '@angular/cdk/overlay';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { LoginComponent } from './components/login/login.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AuthService } from './services/auth.service';
import { InterceptorInterceptor } from './interceptors/interceptor.interceptor';
import { BaseLayoutComponent } from './pages/base-layout/base-layout.component';
import { MatTableModule } from '@angular/material/table'
import { NavbarAdminComponent } from './components/navbar-admin/navbar-admin/navbar-admin.component';
import { NavbarOwnerComponent } from './components/navbar-owner/navbar-owner/navbar-owner.component';

import { AdminModule } from 'src/admin/admin.module';
import { OwnerModule } from 'src/owner/owner.module';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    LoginComponent,
    BaseLayoutComponent,
    NavbarAdminComponent,
    NavbarOwnerComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    OverlayModule,
    AppRoutingModule,
    HttpClientModule,
    AdminModule,
    OwnerModule,
    ToastrModule.forRoot({
      positionClass: 'toast-top-right',
    }),
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatFormFieldModule
  ],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: InterceptorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
