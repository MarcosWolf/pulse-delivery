import { Navigate, createBrowserRouter } from "react-router-dom";
import { LoginPage } from "../modules/auth/pages/LoginPage";
import { NavigationLayout } from "../shared/layouts/NavigationLayout";

const PrivateRoute = ({ children }: { children: React.ReactNode }) => {
    const token = localStorage.getItem("token");

    if (!token) {
        return <Navigate to="/login" replace />
    }

    return children;
};

export const router = createBrowserRouter([
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        path: "/",
        element: (
            <PrivateRoute>
                <NavigationLayout />
            </PrivateRoute>
        ),
        children: [
            {
                index: true,
                element: <Navigate to="/" replace />,
            },
            {
                path: "dashboard",
                element: <Navigate to="/" replace />,
            },
        ],
    },
    {
        path: "*",
        element: <Navigate to="/login" replace />,
    }
]);