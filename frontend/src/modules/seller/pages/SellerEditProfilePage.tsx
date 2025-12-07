import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { SellerEditProfileForm } from "../components/SellerEditProfileForm";
import { useAuthUser } from "../../auth/hooks/useAuthUser";
import { useSeller } from "../hooks/useSeller";

export const SellerEditProfile = () => {
    const navigate = useNavigate();
    const { user } = useAuthUser();

    if (!user) {
        navigate("/");
        return;
    }

    const { seller, loading, setSeller } = useSeller(user.id);

    if (loading) return <div className="text-center mt-6">Loading...</div>;

    if (!seller) {
        navigate("/");
        return;
    }

    return (
        <div>
            Edit profile
        </div>
    );
};