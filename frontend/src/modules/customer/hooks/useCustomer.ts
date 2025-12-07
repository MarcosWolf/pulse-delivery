import { useEffect, useState } from "react";
import { api } from "../../../shared/services/api";
import type { Customer } from "../types/Customer";

export const useCustomer = (customerId: number | null) => {
    const [customer, setCustomer] = useState<Customer | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!customerId) return ;

        api.get(`/customers/${customerId}`)
        .then((res) => setCustomer(res.data))
        .catch((err) => console.error(err))
        .finally(() => setLoading(false));
    }, [customerId]);

    return { customer, loading, setCustomer };
};