import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StyleService {

  constructor() { }

  getLoanStatusCodeStyle(loanStatusCode):string {
    switch (loanStatusCode) {
      case "SUBMITTED": return "badge-secondary";
      case "CREDIT_CHECK": return "badge-secondary";
      case "OFFICER_REVIEW": return "badge-warning";
      case "MANAGER_REVIEW": return "badge-warning";
      case "APPROVED": return "badge-success";
      case "REJECTED": return "badge-danger";
      default: return "badge-text";
    }
  }

  getDecisionCodeStyle(decisionCode): string{
    switch (decisionCode) {
      case "APPROVE": return "badge-success";
      case "REJECT": return "badge-danger";
      default: return "badge-text";
    }
  }
}
