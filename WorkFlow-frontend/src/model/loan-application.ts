import {UserAccount} from "./user-account";
import {LoanStatus} from "./loan-status";
import {Decision} from "./decision";

export class LoanApplication {
    id: number;

    amount: number;

    termMonths: number;

    purpose: string;

    creditScore: number;

    applicant: UserAccount;

    status: LoanStatus;

    processInstanceId: string;

    officerDecision: Decision;

    officerNotes: string;

    managerDecision: Decision;

    managerNotes: string;

    disbursementRef: string;

    createdAt: Date;

    updatedAt: Date;
}
