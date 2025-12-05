import { Outlet } from "react-router-dom";
import { HomeNav } from "../../modules/home/components/HomeNav";

export const HomeLayout = () => {
    return (
        <div className="flex flex-col h-screen bg-gray-100">
            <HomeNav />
            <main className="flex-1 p-6 overflow-auto">
                <Outlet />
            </main>
        </div>
    );
};