import { api } from "../../../shared/services/api";
import type { DeliveryPersonSignupRequest } from "../types/DeliveryPersonSignupRequest";
import type { DeliveryPersonSignupResponse } from "../types/DeliveryPersonSignupResponse";

export class DeliveryPersonSignupService {
  async signup(data: DeliveryPersonSignupRequest): Promise<DeliveryPersonSignupResponse> {
    const response = await api.post<DeliveryPersonSignupResponse>(`/signup`, data);
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