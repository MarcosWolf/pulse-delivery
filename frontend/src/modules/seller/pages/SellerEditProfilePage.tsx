import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { api } from "../../../shared/services/api";
import { SellerEditProfileForm } from "../components/SellerEditProfileForm";
import type { Address } from "../../../shared/types/Address";
import type { User } from "../../user/types/User";
import type { Seller } from "../types/Seller";

type JwtPayload = {
  id: number;
  email: string;
  role: string;
};

export const SellerEditProfile = () => {
    const [seller, setSeller] = useState<Seller | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            console.log("VOLTOU");
            navigate("/");
            return;
        }

        let sellerId: number;

        try {
            const decoded = jwtDecode<JwtPayload>(token);
            sellerId = decoded.id;
        } catch (err) {
            navigate("/");
            return;
        }

        api
            .get(`/sellers/${sellerId}`)
            .then((res) =>  {console.log("Seller API response:", res.data);setSeller(res.data)})
            .catch((err) => console.error(err))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div className="text-center mt-6">Loading...</div>;

    if (!seller || !seller.user) {
        navigate("/");
        return;
    }
    
    const sellerId = seller.user.id;

    return (
        <div className="max-w-5xl mx-auto p-6">
        <SellerEditProfileForm seller={seller} sellerId={sellerId} onUpdate={setSeller} />
        </div>
    );
}