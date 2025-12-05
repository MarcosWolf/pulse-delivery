export type Role = "CUSTOMER" | "SELLER" | "DELIVERYPERSON" ;

export interface DeliveryPersonSignupRequest {
    name: string;
    email: string;
    password: string;
    role: Role;
}