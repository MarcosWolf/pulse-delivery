import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export const HomeNav = () => {
    const navigate = useNavigate();

    return (
        <>
            <header className="w-full bg-white shadow p-6 flex items-center">
                <div className="flex items-center gap-6">
                    <span className="text-orange-600 font-bold text-sm">Pulse Delivery</span>
                    <nav>
                        <ul className="flex gap-4 text-gray-700">
                            <li><Link className="hover:text-orange-600 transition" to="/deliveryperson/signup">Delivery Person</Link></li>
                            <li><Link className="hover:text-orange-600 transition" to="/seller/signup">Restaurant</Link></li>
                        </ul>
                    </nav>
                </div>

                <div className="ml-auto flex items-center gap-6 relative">
                    <Link to="/customer/signup">
                        <button className="flex items-center gap-2 text-gray-700 hover:text-orange-600 transition cursor-pointer">
                            Sign Up
                        </button>
                    </Link>

                    <Link to="/auth/login">
                        <button
                            className="flex items-center gap-2 text-gray-700 hover:text-orange-600 transition cursor-pointer"
                        >
                            Login
                        </button>
                    </Link>
                </div>
            </header>
        </>
    );
};