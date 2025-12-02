import axios from "axios";

export interface UserInfo {
    email: string;
    role: string;
}

export class UserService {
    private readonly baseUrl = "http://localhost:8080";

    getUserInfo(token: string): Promise<UserInfo> {
        return axios
            .get<UserInfo>(`${this.baseUrl}/auth/profile`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            .then(res => res.data);
    }
}