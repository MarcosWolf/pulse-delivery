import type { User } from "../../user/types/User";
import type { Address } from "../../../shared/types/Address";

export type Customer = {
    user: User;
    phone: string;
    address: Address;
};