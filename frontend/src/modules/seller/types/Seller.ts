import type { User } from "../../user/types/User";
import type { Address } from "../../../shared/types/Address";

export type Seller = {
  user: User;
  document: string;
  phone: string;
  address: Address;
  storeImageUrl?: string;
};