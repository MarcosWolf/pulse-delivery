import { Outlet, Link, Navigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { UserService } from "../../modules/users/services/userService";
import type { UserInfo } from "../../modules/users/types/UserInfo";
import { FaSearch, FaUserCircle, FaShoppingCart } from "react-icons/fa";

const userService = new UserService();

export const NavigationLayout = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [cartOpen, setCartOpen] = useState(false);
    const [cartAnimating, setCartAnimating] = useState(false);
    const [user, setUser] = useState<UserInfo | null>();

    const [cartTotal, setCartTotal] = useState(0);
    const [cartItems, setCartItems] = useState([]);

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

     useEffect(() => {
        //const total = cartItems.reduce((acc, item) => acc + item.price * item.qtd, 0);
        const total = 0;
        setCartTotal(total);
    }, [cartItems]);

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    const openCart = () => {
        setCartOpen(true);
        setTimeout(() => setCartAnimating(true), 10);
    };

    const closeCart = () => {
        setCartAnimating(false);
        setTimeout(() => setCartOpen(false), 300);
    };

    return (
        <div className="flex flex-col h-screen bg-gray-100 relative">
            <header className="w-full bg-white shadow p-6 flex items-center">
                <div className="flex items-center gap-6">
                    <span className="text-orange-600 font-bold text-sm">Pulse Delivery</span>
                        <nav>
                            <ul className="flex gap-4 text-gray-700">
                                <li><Link className="hover:text-orange-600 transition" to="/dashboard">Início</Link></li>
                                <li><Link className="hover:text-orange-600 transition" to="/restaurantes">Restaurantes</Link></li>
                                <li><Link className="hover:text-orange-600 transition" to="/mercados">Mercados</Link></li>
                            </ul>
                        </nav>
                    </div>

                <div className="absolute left-1/2 transform -translate-x-1/2 w-full max-w-md">
                    <div className="relative">
                        <span className="absolute inset-y-0 left-3 flex items-center text-gray-400">
                            <FaSearch />
                        </span>
                        <input
                            type="text"
                            placeholder="Busque por item ou loja"
                            className="w-full pl-10 pr-3 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-orange-400"
                        />
                    </div>
                </div>

                <div className="ml-auto flex items-center gap-6 relative">

                    <button
                        onClick={() => setDropdownOpen(!dropdownOpen)}
                        className="flex items-center gap-2 text-gray-700 hover:text-orange-600 transition cursor-pointer"
                    >
                        <FaUserCircle size={24} />
                    </button>

                    {dropdownOpen && (
                        <div className="absolute right-0 top-8 w-100 bg-white shadow-lg rounded-lg border border-gray-200 z-30">
                            <div className="px-8 py-10 border-b border-gray-200 text-gray-700">
                                <p className="text-xl">Olá, {user?.name}!</p>
                            </div>
                            <ul className="flex flex-col">
                                <li><Link className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/pedidos">Pedidos</Link></li>
                                <li><Link className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/meus-dados">Meus dados</Link></li>
                                <li><Link onClick={handleLogout} className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/sair">Sair</Link></li>
                            </ul>
                        </div>
                    )}

                    <button
                        onClick={() => setCartOpen(!cartOpen)}
                        className="flex items-center gap-2 text-gray-700 hover:text-orange-600 transition cursor-pointer"
                    >
                        <FaShoppingCart size={22} />
                        <span className="font-semibold text-sm">
                            R$ {cartTotal.toFixed(2)}
                        </span>
                    </button>
                </div>
            </header>
            <main className="flex-1 p-6 overflow-auto">
                <Outlet />
            </main>
            {cartOpen && (
                <>
                    <div
                        className={`fixed inset-0 bg-black/50 z-40 transition-opacity duration-300 ${
                            cartOpen ? "opacity-100 pointer-events-auto" : "opacity-0 pointer-events-none"
                        }`}
                        onClick={closeCart}
                        />

                    <div
                        className={`fixed right-0 top-0 w-96 h-screen bg-white shadow-2xl z-50 p-6 overflow-auto transform transition-transform duration-300 ${
                            cartOpen ? "translate-x-0" : "translate-x-full"
                        }`}
                        >
                        <div className="flex justify-between items-center mb-6">
                            <h2 className="text-xl font-bold text-gray-700">Meu Carrinho</h2>
                            <button onClick={closeCart} className="text-gray-500 hover:text-gray-700 text-2xl cursor-pointer">
                            ×
                            </button>
                        </div>

                        {cartItems.length === 0 ? (
                            <p className="text-gray-500">Seu carrinho está vazio.</p>
                        ) : (
                            <ul className="flex flex-col gap-4">
                            {cartItems.map((item, idx) => (
                                <li key={idx} className="flex justify-between items-center p-3 border-b border-gray-200">
                                <div>
                                    <p className="font-semibold">PRODUTO</p>
                                    <p className="text-sm text-gray-500">Qtd: 0</p>
                                </div>
                                <span>R$ 0,00</span>
                                </li>
                            ))}
                            </ul>
                        )}

                        <div className="mt-6 border-t pt-4 text-right">
                            <p className="text-lg font-bold text-orange-600">Total: R$ {cartTotal.toFixed(2)}</p>
                        </div>
                    </div>
                </>
            )}

        </div>
    );
};