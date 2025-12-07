import { useState } from "react";
import { DeliveryPersonSignupService } from "../services/DeliveryPersonSignupService";
import type { DeliveryPersonSignupRequest } from "../types/DeliveryPersonSignupRequest";

const service = new DeliveryPersonSignupService();

export const useDeliveryPersonSignup = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const signup = async (data: DeliveryPersonSignupRequest) => {
        setLoading(true);
        setError("");

        try {
            const result = await service.signup(data);

            service.saveToken(result.token);
        } catch (err) {
            setError("Error creating account");
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