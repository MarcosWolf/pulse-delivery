import { useState } from "react";
import { DeliveryPersonSignupService } from "../services/DeliveryPersonSignupService";
import type { DeliveryPersonSignupRequest } from "../types/DeliveryPersonSignupRequest";

interface DeliveryPersonSignupFormProps {
    onSignupSuccess: () => void;
}

const service = new DeliveryPersonSignupService;

export const DeliveryPersonSignupForm = ({ onSignupSuccess }: DeliveryPersonSignupFormProps) => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSignup = async () => {
        try {
            const data: DeliveryPersonSignupRequest = {
                name,
                email,
                password,
                role: "DELIVERYPERSON"
            };

            const result = await service.signup(data);

            const ok = service.saveToken(result.token);
            if (ok) onSignupSuccess();
        } catch (err) {
            setError("Error creating account")
        }
    };

    return (
        <div className="flex flex-col gap-4">
            <input type="text" placeholder="Full name" value={name} 
                onChange={e => setName(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            <input type="email" placeholder="E-mail" value={email} 
                onChange={e => setEmail(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            <input type="password" placeholder="Password" value={password} 
                onChange={e => setPassword(e.target.value)} 
                className="border border-gray-300 rounded-lg p-3 focus:outline-none focus:ring-2 focus:ring-orange-400" 
            />

            {error && <p className="text-red-500 text-sm text-center">{error}</p>}

            <button 
                onClick={handleSignup} 
                className="bg-orange-500 text-white font-semibold py-3 rounded-lg hover:bg-orange-600 transition"
            >
                Create Account
            </button>

        </div>
    )
}