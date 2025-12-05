import { api } from "../../../shared/services/api";
import type { CustomerSignupRequest } from "../../customer/types/CustomerSignupRequest";
import type { CustomerSignupResponse } from "../../customer/types/CustomerSignupResponse";

export class CustomerSignupService {
  async signup(data: CustomerSignupRequest): Promise<CustomerSignupResponse> {
    const response = await api.post<CustomerSignupResponse>(`/signup`, data);
    return response.data;
}

  saveToken(token: string) {
    localStorage.setItem("token", token);
    return true;
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }

  removeToken() {
    localStorage.removeItem("token");
  }
}