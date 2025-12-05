import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { CustomerHomePage } from "../../modules/customer/pages/CustomerHomePage";
import { SellerHomePage } from "../../modules/seller/pages/SellerHomePage";
import { DeliveryPersonHomePage } from "../../modules/deliveryPerson/pages/DeliveryPersonHomePage";

interface JwtPayload {
    sub: string;
    role: string;
}

export const RoleBasedRedirect = () => {
    const token = localStorage.getItem("token");
    
    if (!token) return <Navigate to="/login" replace />;

    try {
        const decoded = jwtDecode<JwtPayload>(token);

        switch (decoded.role) {
            case "CUSTOMER":
                return <CustomerHomePage />;
            case "SELLER":
                return <SellerHomePage />;
            case "DELIVERYPERSON":
                return <DeliveryPersonHomePage />;
            default:
                return <Navigate to="/login" replace />;
        }
    } catch (err) {
        return <Navigate to="/login" replace />;
    }
};