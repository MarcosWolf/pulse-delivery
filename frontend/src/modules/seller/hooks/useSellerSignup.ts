import { useState } from "react";
import { SellerSignupService } from "../services/SellerSignupService";
import type { SellerSignupRequest } from "../types/SellerSignupRequest";

const service = new SellerSignupService();

export const useSellerSignup = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const signup = async (data: SellerSignupRequest) => {
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
