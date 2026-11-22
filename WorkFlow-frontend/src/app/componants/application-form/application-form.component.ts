import { Component } from '@angular/core';
import {LoanApplicationService} from "../../../service/loan-application.service";
import {LoanApplication} from "../../../model/loan-application";
import {map} from "rxjs/internal/operators";
import {Router} from "@angular/router";

@Component({
  selector: 'app-application-form',
  templateUrl: './application-form.component.html',
  styleUrls: ['./application-form.component.css']
})
export class ApplicationFormComponent {
  amountError: string = "";
  termMonthsError: string = "";
  purposeError: string = "";
  messageError: string = "";

  private loanApplication: LoanApplication;
  constructor(private loanApplicationService: LoanApplicationService,
              private router: Router,) {
  }

  submitApplication(amount, termMonths, purpose){
    this.loanApplicationService.submitApplication(amount, termMonths, purpose).subscribe(
       successResponse => {
         this.loanApplication = successResponse;
         this.router.navigateByUrl("applications")
       },
        errorResponse => {
          console.log(errorResponse);
          this.messageError = errorResponse.error.errors.error;
          this.amountError = errorResponse.error.errors.amount;
          this.termMonthsError = errorResponse.error.errors.termMonths;
          this.purposeError = errorResponse.error.errors.purpose;
        }
    );

    setTimeout(
        ()=>{
            this.messageError = "";
            this.amountError = "";
            this.termMonthsError = "";
            this.purposeError = "";
        }, 10000
    );
  }

    isCustomer(): boolean {
        return sessionStorage.getItem('role') === 'CUSTOMER';
    }
}
