import { Outlet } from "react-router-dom";
import { SellerNav } from "../../modules/seller/components/SellerNav";

export const SellerLayout = () => {
    return (
        <div className="flex flex-col h-screen bg-gray-100">
            <SellerNav />
            <main className="flex-1 p-6 overflow-auto">
                <Outlet />
            </main>
        </div>
    );
};