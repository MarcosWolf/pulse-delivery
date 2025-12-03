import { useNavigate } from "react-router-dom";
import { LoginForm } from "../components/LoginForm";

export const LoginPage = () => {
    const navigate = useNavigate();

    const handleLoginSuccess = () => {
        navigate("/dashboard");
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-orange-50">
            <div className="w-full max-w-md bg-white p-8 rounded-xl shadow-lg">
                <h2 className="text-2xl font-bold text-orange-600 mb-6 text-center">
                    Pulse Delivery
                </h2>

                <LoginForm onLoginSuccess={handleLoginSuccess} />
            </div>
        </div>
    );
};
