import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/User';
import { Observable } from 'rxjs';
import { Login } from '../models/Login';
import { Token } from '../models/Token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private headers = new HttpHeaders({ "Content-Type": "application/json"});

  constructor(private http: HttpClient) { }

  login(auth: Login): Observable<string> {
    return this.http.post<string>("backend/api/auth/login", auth, {
      headers: this.headers,
      responseType: "json",
    });
  }

  setCurrentUser(): Observable<User> {
    return this.http.get<User>("backend/api/auth/getLoggedIn", {
      headers: this.headers,
      responseType: "json",
    });
  }

  getCurrentUser(): User | null {

    const jsonString = localStorage.getItem("currentUser");

    if(jsonString) return JSON.parse(jsonString);
    else return null;
  }

  logout(): Observable<string> {
    localStorage.removeItem("currentUser");
    localStorage.removeItem("userToken");

    return this.http.get("backend/api/auth/logOut", {
      headers: this.headers,
      responseType: "text",
    });
  }
}
