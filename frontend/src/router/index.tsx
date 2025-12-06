import { Navigate, createBrowserRouter } from "react-router-dom";
import { Login } from "../modules/auth/pages/LoginPage";

import { HomeLayout } from "../shared/layouts/HomeLayout";
import { CustomerLayout } from "../shared/layouts/CustomerLayout";
import { SellerLayout } from "../shared/layouts/SellerLayout";
import { DeliveryPersonLayout } from "../shared/layouts/DeliveryPersonLayout";
import { PrivateRoute } from "../shared/components/PrivateRoute";

import { Home } from "../modules/home/pages/HomePage";

import { CustomerSignup } from "../modules/customer/pages/CustomerSignupPage";
import { CustomerDashboard } from "../modules/customer/pages/CustomerDashboardPage";

import { SellerSignup } from "../modules/seller/pages/SellerSignupPage";
import { SellerDashboard } from "../modules/seller/pages/SellerDashboardPage";
import { SellerEditProfile } from "../modules/seller/pages/SellerEditProfilePage";

import { DeliveryPersonSignup } from "../modules/deliveryPerson/pages/DeliveryPersonSignupPage";
import { DeliveryPersonDashboard } from "../modules/deliveryPerson/pages/DeliveryPersonDashboardPage";


export const router = createBrowserRouter([
    {
        path: "/",
        element: <HomeLayout />,
        children: [
            { index: true, element: <Home /> },
        ],
    },
    {
        path: "/auth/login",
        element: <Login />,
    },
    {
        path: "/customer/signup",
        element: <CustomerSignup />,
    },
    {
        path: "/seller/signup",
        element: <SellerSignup />,
    },
    {
        path: "/deliveryperson/signup",
        element: <DeliveryPersonSignup />,
    },
    {
        path: "/customer",
        element: (
            <PrivateRoute allowedRoles={["CUSTOMER"]}>
                <CustomerLayout />
            </PrivateRoute>
        ),
        children: [
            { index: true, element: <Navigate to="/customer/dashboard" replace /> },
            { path: "dashboard", element: <CustomerDashboard /> },
        ],
    },

    {
        path: "/seller",
        element: (
            <PrivateRoute allowedRoles={["SELLER"]}>
                <SellerLayout />
            </PrivateRoute>
        ),
        children: [
            { index: true, element: <Navigate to="/seller/dashboard" replace /> },
            { path: "dashboard", element: <SellerDashboard /> },
        ],
    },
    {
        path: "/seller/edit-profile",
        element: (
            <PrivateRoute allowedRoles={["SELLER"]}>
                <SellerLayout />
            </PrivateRoute>
        ),
        children: [
            { index: true, element: <SellerEditProfile /> },
            { path: "dashboard", element: <SellerDashboard /> },
        ],
    },

    {
        path: "/deliveryperson",
        element: (
            <PrivateRoute allowedRoles={["DELIVERYPERSON"]}>
                <DeliveryPersonLayout />
            </PrivateRoute>
        ),
        children: [
            { index: true, element: <Navigate to="/deliveryperson/dashboard" replace /> },
            { path: "dashboard", element: <DeliveryPersonDashboard /> },
        ],
    },

    {
        path: "*",
        element: <Navigate to="/" replace />,
    },
]);