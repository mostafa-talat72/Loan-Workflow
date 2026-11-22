import { Component } from '@angular/core';
import {AuthService} from "../../../service/security/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  messageError: string = "";
  fullNameError: string = "";
  usernameError: string = "";
  emailError: string = "";
  passwordError: string = "";
  confirmPasswordError: string = "";

  constructor(private authService: AuthService,
              private router: Router,) {
  }

  signUp(fullName, username, email, password, confirmPassword): void {
    if(this.isValid(fullName, username, password, confirmPassword) && this.passwordMismatch(password, confirmPassword)) {
      this.authService.signUp(fullName, username, email, password).subscribe(
          successResponse => {
            sessionStorage.setItem("username", successResponse.username);
            sessionStorage.setItem("token", successResponse.token);
            sessionStorage.setItem("role", successResponse.role);
            this.router.navigateByUrl(this.isCustomer() ? "/applications" : "/pending");
          },
          errorResponse => {
            console.log(JSON.stringify(errorResponse));
          }
      )
    }

    setTimeout(
        () => {
          this.messageError = "";
          this.fullNameError = "";
          this.usernameError = "";
          this.emailError = "";
          this.passwordError = "";
          this.confirmPasswordError = ""
        }, 10000
    )
  }

  isValid(fullName, username, password, confirmPassword): boolean{
    if(!fullName){
      this.fullNameError = "Full name is required";
    }
    if(!username){
      this.usernameError = "Username is required";
    }
    if (!password){
      this.passwordError = "Password is required";
    }

    if (!confirmPassword){
      this.confirmPasswordError = "Confirm Password is required";
    }
    return fullName && username && password && confirmPassword;
  }

  passwordMismatch(password, confirmPassword): boolean{
    if(password != confirmPassword){
      this.confirmPasswordError = "Confirm Password is required";
      return false;
    }
    return true;
  }

  isCustomer(): boolean {
    return sessionStorage.getItem('role') === 'CUSTOMER';
  }
}
