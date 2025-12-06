import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

interface PrivateRouteProps {
    children: React.ReactNode;
    allowedRoles: string[];
}

export const PrivateRoute = ({ children, allowedRoles }: PrivateRouteProps) => {
    const token = localStorage.getItem("token");
    
    if (!token) {
        return <Navigate to="/auth/login" replace />;
    }
    
    try {
        const decoded: any = jwtDecode(token);
        const userRole = decoded.role;

        if (!allowedRoles.includes(userRole)) {
            switch (userRole) {
                case "CUSTOMER":
                    return <Navigate to="/customer/dashboard" replace />;
                case "SELLER":
                    return <Navigate to="/seller/dashboard" replace />;
                case "DELIVERY_PERSON":
                    return <Navigate to="/delivery/dashboard" replace />;
                default:
                    return <Navigate to="/" replace />;
            }
        }

        return children;
    } catch (e) {
        return <Navigate to="/auth/login" replace />;
    }
};