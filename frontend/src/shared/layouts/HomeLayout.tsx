import { Outlet } from "react-router-dom";
import { HomeNav } from "../../modules/home/components/HomeNav";

export const HomeLayout = () => {
    return (
        <main className="flex-1 overflow-auto">
            <Outlet />
        </main>
    );
};