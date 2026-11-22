import {Component, OnInit} from '@angular/core';
import {LoanApplicationService} from "../../../service/loan-application.service";
import {Router} from "@angular/router";
import {StyleService} from "../../../service/style.service";
import {LoanApplication} from "../../../model/loan-application";

@Component({
  selector: 'app-pending-applications',
  templateUrl: './pending-applications.component.html',
  styleUrls: ['./pending-applications.component.css']
})
export class PendingApplicationsComponent implements OnInit {

  messageError = '';
  messageWarning = '';
  loading = false;

  selected: any = null;
  decision: 'APPROVE' | 'REJECT' = 'APPROVE';
  notes = '';
  reviewError = '';
  saving = false;
  loanApplications: LoanApplication[] = [];

  constructor(private loanApplicationService: LoanApplicationService,
              private router: Router,
              private styleService: StyleService,) {
  }

  ngOnInit(): void {
    this.getPendingApplication();
  }

  getPendingApplication(){
    this.loanApplicationService.getPendingApplication().subscribe(
        successResponse => {
          this.loanApplications = successResponse;
          if(this.loanApplications.length == 0){
            this.messageWarning = 'No pending applications found.';
            this.loanApplications = null;
          }
        },
        errorResponse => {
          this.messageError = JSON.stringify(errorResponse);
        }
    )
  }

  openReview(id: number, applicantName: string, amount: number, termMonths: number, creditScore: number): void {
    this.selected = { id, applicant: { fullName: applicantName }, amount, termMonths, creditScore };
    this.decision = 'APPROVE';
    this.notes = '';
    this.reviewError = '';
  }

  cancel(): void {
    if (this.saving) {
      return;
    }
    this.selected = null;
  }

  confirm(decision): void {
    if (!this.selected) {
      return;
    }

    this.loanApplicationService.review(
        this.selected.id,
        this.decision,
        this.notes
    ).subscribe(
        successResponse => {
          this.selected = null;
          this.getPendingApplication();
        },
        errorResponse => {
          this.messageError = errorResponse.error.errors.error;
          this.selected = null;
        }
    )
  }

  getLoanStatusCodeStyle(loanStatusCode):string {
    return this.styleService.getLoanStatusCodeStyle(loanStatusCode);
  }
}
