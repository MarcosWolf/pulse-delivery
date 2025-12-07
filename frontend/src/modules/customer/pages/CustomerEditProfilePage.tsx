import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuthUser } from "../../auth/hooks/useAuthUser";
import { useCustomer } from "../hooks/useCustomer";

export const CustomerEditProfile = () => {
    const navigate = useNavigate();
    const { user } = useAuthUser();

    if (!user) {
        navigate("/");
        return;
    }

    const { customer, loading, setCustomer } = useCustomer(user.id);

    if (!customer) {
        navigate("/");
        return;
    }

    return (
        <div>
            Edit profile
        </div>
    )
}