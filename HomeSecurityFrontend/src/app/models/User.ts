import { Role } from "./Role"

export interface User {
    id: number,
    firstName: string,
    lastName: string,
    username: string,
    email: string,
    password: string,
    accountLocked: boolean,
    deleted: boolean,
    role: Role
}