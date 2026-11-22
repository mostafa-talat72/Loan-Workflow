import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LoanApplication} from "../model/loan-application";
import {map} from "rxjs/internal/operators";

@Injectable({
  providedIn: 'root'
})
export class LoanApplicationService {

  baseUrl: string = 'http://localhost:8085/loans';

  constructor(private http: HttpClient) { }

  submitApplication(amount, termMonths, purpose):Observable<LoanApplication>{
    return this.http.post<LoanApplication>(this.baseUrl + "/submit",
        {amount, termMonths, purpose}).pipe(
        map(
            response => response
        )
    );
  }

  getMyApplications():Observable<LoanApplication[]> {
    return this.http.get<LoanApplication[]>(this.baseUrl + "/my").pipe(
        map(
            response => response
        )
    );
  }
  getApplicationById(id):Observable<LoanApplication>{
    return this.http.get<LoanApplication>(this.baseUrl + "/" + id)
        .pipe(
            map(
                response => response
            )
        );
  }


  getPendingApplication():Observable<LoanApplication[]>{
      return this.http.get<LoanApplication[]>(this.baseUrl + "/pending").pipe(
          map(
              response => response
          )
      );
  }

  review(applicationId, decisionCode, notes): Observable<LoanApplication>{
      console.log(sessionStorage.getItem("role"));
      const reviewType = sessionStorage.getItem("role") === "RISK_OFFICER"? "risk-review" : "manager-review";
      return this.http.post<LoanApplication>(`${this.baseUrl}/${applicationId}/${reviewType}`,
          {decisionCode, notes}).pipe(
          map(
              response => response
          )
      );
  }
}
