export type Role = "CUSTOMER" | "SELLER" | "DELIVERYPERSON" ;

export interface CustomerSignupResponse {
    id: number;
    email: string;
    role: Role;
    token: string;
}