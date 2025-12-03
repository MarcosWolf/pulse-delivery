import { useState } from "react";
import { AuthService } from "../services/authService";
import type { LoginRequest } from "../types/LoginRequest";

interface LoginFormProps {
    onLoginSuccess: () => void;
}

const authService = new AuthService();

export const LoginForm = ({ onLoginSuccess }: LoginFormProps) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async () => {
        try {
            const data: LoginRequest = { email, password };
            const result = await authService.login(data);
            authService.saveToken(result.token);
            onLoginSuccess();
        } catch (err) {
            setError("Invalid credentials");
        }
    };

    return (
        <div className="flex flex-col gap-4">
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
                onClick={handleLogin}
                className="bg-orange-500 text-white font-semibold py-3 rounded-lg hover:bg-orange-600 transition"
            >
                Login
            </button>
        </div>
    );
};