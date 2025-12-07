import { useState } from "react";
import { CustomerSignupService } from "../services/CustomerSignupService";
import type { CustomerSignupRequest } from "../types/CustomerSignupRequest";

const service = new CustomerSignupService();

export const useCustomerSignup = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const signup = async (data: CustomerSignupRequest) => {
        setLoading(true);
        setError("");

        try {
            const result = await service.signup(data);

            service.saveToken(result.token);
            return true;
        } catch (err) {
            setError("Error creating account");
            return false;
        } finally {
            setLoading(false);
        }
    };

    return {
        signup,
        loading,
        error,
    };
};