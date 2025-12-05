export type Role = "CUSTOMER" | "SELLER" | "DELIVERYPERSON" ;

export interface CustomerSignupRequest {
    name: string;
    email: string;
    password: string;
    role: Role;
}