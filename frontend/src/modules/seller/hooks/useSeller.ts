import { useEffect, useState } from "react";
import { api } from "../../../shared/services/api";
import type { Seller } from "../types/Seller";

export const useSeller = (sellerId: number | null) => {
    const [seller, setSeller] = useState<Seller | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!sellerId) return;

        api.get(`/sellers/${sellerId}`)
            .then((res) => setSeller(res.data))
            .catch((err) => console.error(err))
            .finally(() => setLoading(false));

        }, [sellerId]);

    return { seller, loading, setSeller };
};