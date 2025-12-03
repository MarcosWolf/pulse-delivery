import { Outlet, Link } from "react-router-dom";
import { useState } from "react";
import { FaSearch, FaUserCircle } from "react-icons/fa"; // ícone de pessoa

export const AppLayout = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);

    return (
        <div className="flex flex-col h-screen bg-gray-100">
            <header className="w-full bg-white shadow p-6 flex items-center relative">
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

                <div className="ml-auto relative">
                    <button
                        onClick={() => setDropdownOpen(!dropdownOpen)}
                        className="flex items-center gap-2 text-gray-700 hover:text-orange-600 transition cursor-pointer"
                    >
                        <FaUserCircle size={24} />
                    </button>

                    {dropdownOpen && (
                        <div className="absolute right-0 mt-2 w-100 bg-white shadow-lg rounded-lg border border-gray-200 z-10">
                            <div className="px-8 py-10 border-b border-gray-200 text-gray-700">
                                <p className="text-xl">Olá, NOME_PLACEHOLDER!</p>
                            </div>
                            <ul className="flex flex-col">
                                <li><Link className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/pedidos">Pedidos</Link></li>
                                <li><Link className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/meus-dados">Meus dados</Link></li>
                                <li><Link className="block px-4 py-2 hover:bg-orange-50 hover:text-orange-600 transition" to="/sair">Sair</Link></li>
                            </ul>
                        </div>
                    )}
                </div>

            </header>

        <main className="flex-1 p-6 overflow-auto">
            <Outlet />
        </main>

        </div>
    );
};
