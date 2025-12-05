import { useNavigate } from "react-router-dom";
import { DeliveryPersonSignupForm } from "../components/DeliveryPersonSignupForm";

export const DeliveryPersonSignupPage = () => {
    const navigate = useNavigate();

    const handleSignupSuccess = () => {
        navigate("/");
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-orange-50">
            <div className="w-full max-w-md bg-white p-8 rounded-xl shadow-lg">
                <h2 className="text 2xl font-bold text-orange-600 mb-6 text-center">
                    Delivery Person
                </h2>
                <h2 className="text 2xl font-bold text-orange-600 mb-6 text-center">
                    Create Account
                </h2>

                <DeliveryPersonSignupForm onSignupSuccess={handleSignupSuccess} />
            </div>
        </div>
    );
};