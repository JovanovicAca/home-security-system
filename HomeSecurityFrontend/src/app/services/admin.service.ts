import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Csr } from '../models/Csr';
import { NewCsr } from '../models/NewCsr';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Certificate } from '../models/Certificate';
import { User } from '../models/User';
import { NewUser } from '../models/NewUser';
import { RealEstate } from '../models/RealEstate';
import { NewRealEstate } from '../models/NewRealEstate';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private headers = new HttpHeaders({ "Content-Type": "application/json" });

  constructor(
    private http: HttpClient,
    private toastr: ToastrService,
    private router: Router
  ) { }

  generate(csr: NewCsr): void {
    console.log(csr);
    this.http.post<Csr>("backend/api/csr", csr, {
      headers: this.headers,
      responseType: "json",
    }).subscribe((response) => {
      this.toastr.success("Verification email has been sent ");
    }, (error) => {
      this.toastr.error("Error");
    }
    );
  }

  getPending(): Observable<Csr[]> {
    return this.http.get<Csr[]>("backend/api/csr", {
      headers: this.headers,
      responseType: "json",
    })
  }

  acceptCsr(id: number): void {
    this.http.post("backend/api/csr/accept/" + id, {
      headers: this.headers,
      responseType: "json",
    }).subscribe(() => {
      window.location.reload();
    });
  }

  declineCsr(id: number): void {
    this.http.post("backend/api/csr/decline/" + id, {
      headers: this.headers,
      responseType: "json",
    }).subscribe(() => {
      window.location.reload();
    });
  }

  getCertificates(): Observable<Certificate[]> {
    return this.http.get<Certificate[]>("backend/api/certificate", {
      headers: this.headers,
      responseType: "json",
    })
  }

  revoke(serialNumber: number, reason: string, email: string): void {
    this.http.post("backend/api/certificate/" + serialNumber + "/" + reason + "/" + email, {
      headers: this.headers,
      responseType: "json",
    }).subscribe(() => {
      window.location.reload();
    });
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>("backend/api/user", {
      headers: this.headers,
      responseType: "json",
    })
  }

  getOwners(): Observable<User[]> {
    return this.http.get<User[]>("backend/api/user/owners", {
      headers: this.headers,
      responseType: "json",
    })
  }

  getTenants(): Observable<User[]> {
    return this.http.get<User[]>("backend/api/user/tenants", {
      headers: this.headers,
      responseType: "json",
    })
  }

  deleteUser(username: string): void {
    this.http.post("backend/api/user/delete/" + username, {
      headers: this.headers,
      responseType: "json",
    }).subscribe(() => {
      window.location.reload();
    });
  }

  createUser(user: NewUser): void {
    this.http.post<User>('backend/api/user/new-user', user, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('User created successfully!');
      });
  }

  updateUser(user: NewUser): void {
    this.http.post<User>('backend/api/user/update-user', user, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('User updated successfully!');
      });
  }

  lockAccount(username: string): void {
    this.http.post<User>('backend/api/user/lock/' + username, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.error('Account locked!');
      });
  }

  getUserRealEstates(id: number): Observable<RealEstate[]> {
    return this.http.get<RealEstate[]>("backend/api/user/real-estate/" + id, {
      headers: this.headers,
      responseType: "json",
    })
  }

  editAccessToRealEstate(realEstate: RealEstate): void {
    this.http.post<RealEstate>('backend/api/user/access', realEstate, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('Changes saved!');
      });
  }

  getAllRealEstates(): Observable<RealEstate[]> {
    return this.http.get<RealEstate[]>("backend/api/real-estate", {
      headers: this.headers,
      responseType: "json",
    })
  }

  getUsersInRealEstate(id: number): Observable<User[]> {
    return this.http.get<User[]>("backend/api/real-estate/users/" + id, {
      headers: this.headers,
      responseType: "json",
    })
  }

  createRealEstate(realEstate: NewRealEstate): void {
    this.http.post<User>('backend/api/real-estate/new-real-estate', realEstate, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('Real estate created successfully!');
      });
  }

  getRealEstate(id: number): Observable<RealEstate> {
    return this.http.get<RealEstate>("backend/api/real-estate/" + id, {
      headers: this.headers,
      responseType: "json",
    })
  }

  addRealEstateToUser(realEstateId: number, userId: number): void {
    this.http.post<User>('backend/api/real-estate/user/' + userId + "/" + realEstateId, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('House updated successfully!');
      });
  }

  removeUserFromRealEstate(realEstateId: number, userId: number): void {
    this.http.post<User>('backend/api/real-estate/remove-user/' + userId + "/" + realEstateId, {
      headers: this.headers,
      responseType: 'json',
    })
      .subscribe(() => {
        this.toastr.success('House updated successfully!');
      });
  }

}
