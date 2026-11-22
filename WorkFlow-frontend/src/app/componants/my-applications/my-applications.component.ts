import {Component, OnInit} from '@angular/core';
import {LoanApplication} from "../../../model/loan-application";
import {LoanApplicationService} from "../../../service/loan-application.service";
import {Router} from "@angular/router";
import {StyleService} from "../../../service/style.service";

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html',
  styleUrls: ['./my-applications.component.css']
})
export class MyApplicationsComponent implements OnInit {
    loanApplications: LoanApplication[];
    messageError: string = "";
    messageWarning: string = "";
    loading: boolean = false;
    constructor(private loanApplicationService: LoanApplicationService,
                private router: Router, private styleService: StyleService) {
    }

    ngOnInit(): void {
        this.getMyApplications();
    }

  getMyApplications(): void {
      this.loanApplicationService.getMyApplications().subscribe(
          successResponse => {
              this.loanApplications = successResponse;
              if(this.loanApplications.length == 0){
                  this.messageWarning = "You don't have any application yet";
                  this.loanApplications = null;
              }
          },
          errorResponse => {
              this.messageError = errorResponse.error.errors.error;
          }
      )
  }

  getLoanStatusCodeStyle(loanStatusCode):string {
       return this.styleService.getLoanStatusCodeStyle(loanStatusCode);
  }
}
