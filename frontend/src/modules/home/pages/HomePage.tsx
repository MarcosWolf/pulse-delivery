import { Navigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

interface jwtPayload {
    sub: string;
    role: "CUSTOMER" | "SELLER" | "DELIVERYPERSON";
    exp: number;
}

export const Home = () => {
    const token = localStorage.getItem("token");
    if (token != null) {
        const decoded = jwtDecode<jwtPayload>(token);

        if (token && decoded) {
            switch (decoded.role) {
                case "CUSTOMER":
                    return <Navigate to="/customer/dashboard" replace />;
                case "SELLER":
                    return <Navigate to="/seller/dashboard" replace />;
                case "DELIVERYPERSON":
                    return <Navigate to="/deliveryperson/dashboard" replace />;
                default:
                    localStorage.removeItem("token");
                    localStorage.removeItem("userType");
            }
        }
    }

    return (
        <div className="min-h-screen bg-gray-900 text-white">
            <div className="container mx-auto px-4 py-6">
                <nav className="flex items-center justify-between mb-16">
                <div className="flex items-center gap-3">
                    <div className="w-12 h-12 bg-yellow-600 rounded-full flex items-center justify-center text-2xl">
                    🍽️
                    </div>
                    <span className="text-2xl font-bold">Pulse Delivery</span>
                </div>
                <div className="flex gap-4">
                    <Link to="/deliveryperson/signup">
                        <button className="px-6 py-2 border-2 border-yellow-600 text-yellow-600 rounded-lg hover:bg-yellow-600 hover:text-gray-900 transition-all font-semibold cursor-pointer">
                            Entregadores
                        </button>
                    </Link>
                    <Link to="/seller/signup">
                        <button className="px-6 py-2 border-2 border-yellow-600 text-yellow-600 rounded-lg hover:bg-yellow-600 hover:text-gray-900 transition-all font-semibold cursor-pointer">
                            Restaurantes
                        </button>
                    </Link>
                </div>
                </nav>

                <div className="text-center mb-16">
                <h1 className="text-6xl font-bold mb-6">
                    Peça comida do conforto<br />da sua <span className="text-yellow-600">casa</span>
                </h1>
                <p className="text-xl text-gray-400 mb-12">
                    Centenas de restaurantes esperando por você
                </p>
                <div className="flex gap-6 justify-center">
                    <Link to="/auth/login">
                        <button
                        className="px-10 py-4 bg-yellow-600 text-gray-900 rounded-lg font-bold text-lg hover:bg-yellow-500 transition-all cursor-pointer"
                        >
                            Entrar
                        </button>
                    </Link>
                    <Link to="/customer/signup">
                        <button className="px-10 py-4 border-2 border-yellow-600 text-yellow-600 rounded-lg font-bold text-lg hover:bg-yellow-600 hover:text-gray-900 transition-all cursor-pointer">
                            Cadastrar
                        </button>
                    </Link>                    
                </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mt-20">
                <div className="bg-gray-800 p-8 rounded-2xl border border-gray-700">
                    <div className="text-5xl mb-4">🍕</div>
                    <h3 className="text-2xl font-bold mb-3">Pizzas Incríveis</h3>
                    <p className="text-gray-400">Mais de 50 sabores disponíveis</p>
                </div>
                <div className="bg-gray-800 p-8 rounded-2xl border border-gray-700">
                    <div className="text-5xl mb-4">🍔</div>
                    <h3 className="text-2xl font-bold mb-3">Burgers Artesanais</h3>
                    <p className="text-gray-400">Feitos com ingredientes frescos</p>
                </div>
                <div className="bg-gray-800 p-8 rounded-2xl border border-gray-700">
                    <div className="text-5xl mb-4">🍣</div>
                    <h3 className="text-2xl font-bold mb-3">Comida Japonesa</h3>
                    <p className="text-gray-400">Sushis e sashimis fresquinhos</p>
                </div>
                </div>
            </div>
            </div>
    );
};