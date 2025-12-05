export type Role = "CUSTOMER" | "SELLER" | "DELIVERYPERSON" ;

export interface SellerSignupRequest {
    name: string;
    email: string;
    password: string;
    role: Role;
}