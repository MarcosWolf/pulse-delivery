import axios from "axios";
import type { LoginRequest } from "../types/LoginRequest";
import type { LoginResponse } from "../types/LoginResponse";

export class AuthService {
  private readonly baseUrl = "http://localhost:8080/auth";

  async login(data: LoginRequest): Promise<LoginResponse> {
    const response = await axios.post<LoginResponse>(`${this.baseUrl}/login`, data);
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