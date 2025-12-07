import type { User } from "../../user/types/User";
import type { Address } from "../../../shared/types/Address";
import type { Image } from "../../../shared/types/Image";

export type DeliveryPerson = {
    user: User;
    document: string;
    phone: string;
    address: Address;
    image: Image;
};