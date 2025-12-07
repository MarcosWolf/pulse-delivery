import { useMemo } from "react";
import { AuthService } from "../services/authService";

const authService = new AuthService();

export const useAuthUser = () => {
    const user = useMemo(() => {
            return authService.getDecodedUser();
    }, []);

    return { user };
};