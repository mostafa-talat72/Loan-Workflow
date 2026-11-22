import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {map} from "rxjs/internal/operators";
import {HttpClient} from "@angular/common/http";
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl: string = 'http://localhost:8085/auth';

  constructor(private http: HttpClient) { }

  login(username, password): Observable<any>{

   return this.http.post<any>(this.baseUrl + '/login',
        {username, password},
   ).pipe(
            map(
                response => response
            )
    );
  }

  signUp(fullName, username, email, password): Observable<any>{
      return this.http.post<any>(this.baseUrl + '/signup',
          {fullName,username,email, password},
      ).pipe(
          map(
              response => response
          )
      );
  }

  isUserLogin(): boolean{
      return sessionStorage.getItem("token") !== null
                && sessionStorage.getItem("token") !== undefined
                && sessionStorage.getItem("token") !== "";
  }

  logout(){
      sessionStorage.removeItem("username");
      sessionStorage.removeItem("token");
      sessionStorage.removeItem("role");
  }
}
