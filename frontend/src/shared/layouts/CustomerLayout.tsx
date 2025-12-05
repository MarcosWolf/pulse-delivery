import { Outlet } from "react-router-dom";
import { CustomerNav } from "../../modules/customer/components/CustomerNav";

export const CustomerLayout = () => {
    return (
        <div className="flex flex-col h-screen bg-gray-100">
            <CustomerNav />
            <main className="flex-1 p-6 overflow-auto">
                <Outlet />
            </main>
        </div>
    );
};