import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthService } from "../Services/authService";
import type { LoginRequest } from "../Services/authService";

const authService = new AuthService();

export const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const data: LoginRequest = { email, password };
            const result = await authService.login(data);
            authService.saveToken(result.token);
            navigate("/dashboard")
        } catch (err) {
            setError("Invalid credentials");
        }
    }

    return (
        <div>
            <h2>Login</h2>
            <input placeholder="email" value={email} onChange={e => setEmail(e.target.value)} />
            <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
            <button onClick={handleLogin}>Login</button>
            {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
    );
}