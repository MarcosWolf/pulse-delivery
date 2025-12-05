import axios from "axios";
import type { CustomerSignupRequest } from "../../customer/types/CustomerSignupRequest";
import type { CustomerSignupResponse } from "../../customer/types/CustomerSignupResponse";

export class CustomerSignupService {
  private readonly baseUrl = "http://localhost:8080/auth";

  async signup(data: CustomerSignupRequest): Promise<CustomerSignupResponse> {
    const response = await axios.post<CustomerSignupResponse>(`${this.baseUrl}/signup`, data);
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