import {Role} from "./role";

export class UserAccount {
    id: number;

    username: string;

    fullName: string;

    email: string;

    role: Role;

    creditScore: number;

    enabled: boolean;
}
