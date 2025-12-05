import { useState } from "react";
import { jwtDecode } from "jwt-decode";
import { api } from "../../../shared/services/api";
import type { ChangeEvent, FormEvent } from "react";
import type { Address } from "../../../shared/types/Address";
import type { User } from "../../user/types/User";
import type { Seller } from "../types/Seller"; 

type Props = {
  seller: Seller;
  sellerId: number;
  onUpdate?: (updatedSeller: Seller) => void;
};

export const SellerEditProfileForm = ({ seller: initialSeller, sellerId, onUpdate }: Props) => {
  const [seller, setSeller] = useState<Seller>(initialSeller);
  const [storeImage, setStoreImage] = useState<File | null>(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    if (name.startsWith("user.")) {
      const key = name.split(".")[1];
      setSeller({ ...seller, user: { ...seller.user, [key]: value } });
    } else if (name.startsWith("address.")) {
      const key = name.split(".")[1];
      setSeller({ ...seller, address: { ...seller.address, [key]: value } });
    } else {
      setSeller({ ...seller, [name]: value });
    }
  };

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setStoreImage(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      await api.patch(`/sellers/${sellerId}`, {
        name: seller.user.name,
        email: seller.user.email,
        document: seller.document,
        phone: seller.phone,
      });

      await api.patch(`/sellers/${sellerId}/address`, seller.address);

      if (storeImage) {
        const formData = new FormData();
        formData.append("storeImage", storeImage);

        await api.patch(`/sellers/${sellerId}/image`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });
      }

      alert("Seller updated successfully!");
      if (onUpdate) onUpdate(seller);
    } catch (err) {
      console.error(err);
      alert("Error updating seller.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Name</label>
          <input
            type="text"
            name="user.name"
            value={seller.user.name}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Email</label>
          <input
            type="email"
            name="user.email"
            value={seller.user.email}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Document (CNPJ)</label>
          <input
            type="text"
            name="document"
            value={seller.document}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Phone</label>
          <input
            type="text"
            name="phone"
            value={seller.phone}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700">Street</label>
          <input
            type="text"
            name="address.street"
            value={seller.address.street}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Number</label>
          <input
            type="text"
            name="address.number"
            value={seller.address.number}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>

        <div className="md:col-span-2">
          <label className="block text-sm font-medium text-gray-700">Complement</label>
          <input
            type="text"
            name="address.complement"
            value={seller.address.complement || ""}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Neighborhood</label>
          <input
            type="text"
            name="address.neighborhood"
            value={seller.address.neighborhood}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">City</label>
          <input
            type="text"
            name="address.city"
            value={seller.address.city}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">State</label>
          <input
            type="text"
            name="address.state"
            value={seller.address.state}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Postal Code</label>
          <input
            type="text"
            name="address.postalCode"
            value={seller.address.postalCode}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700">Country</label>
          <input
            type="text"
            name="address.country"
            value={seller.address.country}
            onChange={handleChange}
            className="mt-1 block w-full border border-gray-300 rounded-md p-2 focus:ring-orange-500 focus:border-orange-500"
            required
          />
        </div>
      </div>

      <div>
        <label className="block text-sm font-medium text-gray-700">Store Image</label>
        <input
          type="file"
          accept="image/*"
          onChange={handleFileChange}
          className="mt-1 block w-full text-gray-700"
        />
        {seller.storeImageUrl && (
          <img
            src={seller.storeImageUrl}
            alt="Store"
            className="mt-2 h-32 w-32 object-cover rounded-md border"
          />
        )}
      </div>

      <button
        type="submit"
        disabled={loading}
        className="w-full bg-orange-600 text-white font-bold py-2 px-4 rounded-md hover:bg-orange-700 transition"
      >
        {loading ? "Saving..." : "Save Changes"}
      </button>
    </form>
  );
};
