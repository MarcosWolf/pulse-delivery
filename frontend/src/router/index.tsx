import { Navigate, createBrowserRouter } from "react-router-dom";
import { LoginPage } from "../modules/auth/pages/LoginPage";
import { NavigationLayout } from "../shared/layouts/NavigationLayout";
import { CustomerSignupPage } from "../modules/customer/pages/CustomerSignupPage";
import { SellerSignupPage } from "../modules/seller/pages/SellerSignupPage";
import { RoleBasedRedirect } from "../shared/pages/RoleBasedRedirect";

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
        path: "/customer/signup",
        element: <CustomerSignupPage />,
    },
    {
        path: "/seller/signup",
        element: <SellerSignupPage />,
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
                element: <RoleBasedRedirect />,
            },
        ],
    },
    {
        path: "*",
        element: <Navigate to="/login" replace />,
    }
]);