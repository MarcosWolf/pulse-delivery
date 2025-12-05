import axios from "axios";
import type { DeliveryPersonSignupRequest } from "../types/DeliveryPersonSignupRequest";
import type { DeliveryPersonSignupResponse } from "../types/DeliveryPersonSignupResponse";

export class DeliveryPersonSignupService {
  private readonly baseUrl = "http://localhost:8080/auth";

  async signup(data: DeliveryPersonSignupRequest): Promise<DeliveryPersonSignupResponse> {
    const response = await axios.post<DeliveryPersonSignupResponse>(`${this.baseUrl}/signup`, data);
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