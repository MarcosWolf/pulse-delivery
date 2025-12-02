import axios from "axios";

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export class AuthService {
  private readonly baseUrl = "http://localhost:8080/auth";

  async login(data: LoginRequest): Promise<LoginResponse> {
    const response = await axios.post<LoginResponse>(`${this.baseUrl}/login`, data);
    return response.data;
  }

  saveToken(token: string) {
    localStorage.setItem("token", token);
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }
}