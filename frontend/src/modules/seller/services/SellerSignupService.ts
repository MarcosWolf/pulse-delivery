import axios from "axios";
import type { SellerSignupRequest } from "../types/SellerSignupRequest";
import type { SellerSignupResponse } from "../types/SellerSignupResponse";

export class SellerSignupService {
  private readonly baseUrl = "http://localhost:8080/auth";

  async signup(data: SellerSignupRequest): Promise<SellerSignupResponse> {
    const response = await axios.post<SellerSignupResponse>(`${this.baseUrl}/signup`, data);
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