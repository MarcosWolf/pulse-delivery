import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserService } from "../Services/userService";
import type { UserInfo } from "../Services/userService";

const userService = new UserService();

export const Dashboard = () => {
    const [user, setUser] = useState<UserInfo | null>();

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
            const token = localStorage.getItem("token");
            if (!token) {
                navigate("/");
                return;
            }

            const userData = await userService.getUserInfo(token);
            setUser(userData);
        };

        fetchUser();
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    if (!user) return <p>Loading...</p>;

    return (
        <div>
            <h2>Welcome, {user.email}</h2>
            <p>Role: {user.role}</p>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};
