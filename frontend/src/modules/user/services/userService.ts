import { api } from "../../../shared/services/api";
import type { UserInfo } from "../types/UserInfo";

export class UserService {
    getUserInfo(token: string): Promise<UserInfo> {
        return api.get<UserInfo>(`/auth/profile`, {
                headers: { Authorization: `Bearer ${token}` }
            })
            .then(res => res.data);
    }
}