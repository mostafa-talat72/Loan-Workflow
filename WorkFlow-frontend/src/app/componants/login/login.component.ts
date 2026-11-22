import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../service/security/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
    messageError: string = "";
    usernameError = "";
    passwordError = "";
  constructor(private authService: AuthService, private router: Router) {
  }

  login(username, password){
    if(this.isValid(username, password)){
        this.authService.login(username, password).subscribe(
            successResponse => {

                sessionStorage.setItem("username", successResponse.username);
                sessionStorage.setItem("token", successResponse.token);
                sessionStorage.setItem("role", successResponse.role);
                this.router.navigateByUrl(this.isCustomer()?"/applications": "/pending");
            },
            errorResponse => {
                this.messageError = errorResponse.error.errors.error;
            }
        );
    }
    setTimeout(
        ()=>{
            this.messageError = "";
            this.usernameError = "";
            this.passwordError = "";
        }, 10000
    )
  }

  isValid(username, password): boolean{
      if(!username){
          this.usernameError = "Username is required";
      }
      if (!password){
          this.passwordError = "Password is required";
      }

      return username && password;
  }

    isCustomer(): boolean {
        return sessionStorage.getItem('role') === 'CUSTOMER';
    }
}
