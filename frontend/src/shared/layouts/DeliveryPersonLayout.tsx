import { Outlet } from "react-router-dom";
import { DeliveryPersonNav } from "../../modules/deliveryPerson/components/DeliveryPersonNav";

export const DeliveryPersonLayout = () => {
    return (
        <div className="flex flex-col h-screen bg-gray-100">
            <DeliveryPersonNav />
            <main className="flex-1 p-6 overflow-auto">
                <Outlet />
            </main>
        </div>
    );
};