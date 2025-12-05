import { Navigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

interface MyJwtPayload {
    sub: string;
    role: "CUSTOMER" | "SELLER" | "DELIVERYPERSON";
    exp: number;
}

export const Home = () => {
    const token = localStorage.getItem("token");
    if (token != null) {
        const decoded = jwtDecode<MyJwtPayload>(token);

        if (token && decoded) {
            switch (decoded.role) {
                case "CUSTOMER":
                    return <Navigate to="/customer/dashboard" replace />;
                case "SELLER":
                    return <Navigate to="/seller/dashboard" replace />;
                case "DELIVERYPERSON":
                    return <Navigate to="/deliveryperson/dashboard" replace />;
                default:
                    localStorage.removeItem("token");
                    localStorage.removeItem("userType");
            }
        }
    }

    return (
        <div>
            <h2 className="text-3xl font-bold text-orange-600">
                Bem-vindo ao Pulse Delivery
            </h2>
            <p className="mt-4 text-gray-600">
                Faça login ou crie sua conta para começar
            </p>
        </div>
    );
};