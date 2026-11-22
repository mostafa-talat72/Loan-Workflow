import {Component, OnInit} from '@angular/core';
import {LoanApplicationService} from "../../../service/loan-application.service";
import {LoanApplication} from "../../../model/loan-application";
import {ActivatedRoute} from "@angular/router";
import {StyleService} from "../../../service/style.service";

@Component({
  selector: 'app-application-details',
  templateUrl: './application-details.component.html',
  styleUrls: ['./application-details.component.css']
})
export class ApplicationDetailsComponent implements OnInit {

  loanApplication: LoanApplication;
  messageError: string = "";
  constructor(private loanApplicationService: LoanApplicationService,
              private activatedRoute: ActivatedRoute,private styleService: StyleService) {
  }

  ngOnInit(): void {
    const idExists: boolean = this.activatedRoute.snapshot.paramMap.has('id');
    if(idExists){
      this.getLoanApplication(this.activatedRoute.snapshot.paramMap.get('id'));
    }
  }

  getLoanApplication(id) {
    this.loanApplicationService.getApplicationById(id).subscribe(
        successResponse => {
          this.loanApplication = successResponse;
        },
        errorResponse => {
          this.messageError = errorResponse.error?.errors?.error || 'Please try again.';
        }
    )
  }

  getLoanStatusCodeStyle(loanStatusCode):string {
    return this.styleService.getLoanStatusCodeStyle(loanStatusCode);
  }

  getDecisionCodeStyle(decisionCode): string{
    return this.styleService.getDecisionCodeStyle(decisionCode);
  }

  isCustomer(): boolean {
    return sessionStorage.getItem('role') === 'CUSTOMER';
  }
}
