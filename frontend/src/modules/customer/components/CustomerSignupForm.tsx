import { useState } from "react";
import { useCustomerSignup } from "../hooks/useCustomerSignup";

interface CustomerSignupFormProps {
    onSignupSuccess: () => void;
}

export const CustomerSignupForm = ({ onSignupSuccess }: CustomerSignupFormProps) => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const { signup, loading, error } = useCustomerSignup();

    const handleSignup = async () => {
        const success = await signup({
            name,
            email,
            password,
            role: "CUSTOMER",
        });

        if (success) {
            onSignupSuccess();
        }
    };

    return (
        <div className="flex flex-col gap-4">
            <input
                type="text"
                placeholder="Full name"
                value={name} 
                onChange={e => setName(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            <input 
                type="email"
                placeholder="E-mail"
                value={email} 
                onChange={e => setEmail(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            <input
                type="password"
                placeholder="Password"
                value={password} 
                onChange={e => setPassword(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            {error && <p className="text-red-500 text-sm text-center">{error}</p>}

            <button
                disabled={loading} 
                onClick={handleSignup} 
                className="bg-orange-500 text-white font-semibold py-3 rounded-lg hover:bg-orange-600 transition"
            >
                {loading ? "Creating..." : "Create Account"}
            </button>
        </div>
    );
};