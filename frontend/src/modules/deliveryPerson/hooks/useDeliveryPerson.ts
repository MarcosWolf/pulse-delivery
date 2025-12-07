import { useEffect, useState } from "react";
import { api } from "../../../shared/services/api";
import type { DeliveryPerson } from "../types/DeliveryPerson";

export const useDeliveryPerson = (deliveryPersonId: number | null) => {
    const [deliveryPerson, setDeliveryPerson] = useState<DeliveryPerson | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!deliveryPersonId) return;

        api.get(`/delivery-persons/${deliveryPersonId}`)
        .then((res) => setDeliveryPerson(res.data))
        .catch((err) => console.error(err))
        .finally(() => setLoading(false));
    }, [deliveryPersonId]);

    return { deliveryPerson, loading, setDeliveryPerson };
};