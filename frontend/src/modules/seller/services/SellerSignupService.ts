import { api } from "../../../shared/services/api";
import type { SellerSignupRequest } from "../types/SellerSignupRequest";
import type { SellerSignupResponse } from "../types/SellerSignupResponse";

export class SellerSignupService {

  async signup(data: SellerSignupRequest): Promise<SellerSignupResponse> {
    const response = await api.post<SellerSignupResponse>(`auth/signup`, data);
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