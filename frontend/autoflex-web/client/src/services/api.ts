import axios from "axios";

const BASE_URL = import.meta.env.VITE_API;

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  timeout: 15000,
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.message ||
      error.response?.data?.error ||
      error.message ||
      "An unexpected error occurred";
    return Promise.reject(new Error(message));
  },
);

export interface Product {
  id: number;
  code: string;
  name: string;
  price: number;
  rawMaterials?: ProductRawMaterial[];
}

export interface RawMaterial {
  id: number;
  code: string;
  name: string;
  stockQuantity: number;
}

export interface ProductRawMaterial {
  id?: number;
  rawMaterialId: number;
  rawMaterialCode?: string;
  rawMaterialName?: string;
  requiredQuantity: number;
}

export interface ProductionSuggestionItem {
  productId: number;
  productCode: string;
  productName: string;
  unitPrice: number;
  quantityToProduce: number;
  totalValue: number;
}

export interface ProductionSuggestion {
  items: ProductionSuggestionItem[];
  totalValue: number;
}

export const productsService = {
  getAll: () => api.get<Product[]>("/api/products").then((r) => r.data),
  getById: (id: number) =>
    api.get<Product>(`/api/products/${id}`).then((r) => r.data),
  create: (data: Omit<Product, "id">) =>
    api.post<Product>("/api/products", data).then((r) => r.data),
  update: (id: number, data: Partial<Omit<Product, "id">>) =>
    api.put<Product>(`/api/products/${id}`, data).then((r) => r.data),
  delete: (id: number) => api.delete(`/api/products/${id}`),
  addRawMaterial: (
    productId: number,
    data: { rawMaterialId: number; requiredQuantity: number },
  ) =>
    api
      .post(`/api/products/${productId}/raw-materials`, data)
      .then((r) => r.data),
};

export const rawMaterialsService = {
  getAll: () =>
    api.get<RawMaterial[]>("/api/raw-materials").then((r) => r.data),
  getById: (id: number) =>
    api.get<RawMaterial>(`/api/raw-materials/${id}`).then((r) => r.data),
  create: (data: Omit<RawMaterial, "id">) =>
    api.post<RawMaterial>("/api/raw-materials", data).then((r) => r.data),
  update: (id: number, data: Partial<Omit<RawMaterial, "id">>) =>
    api.put<RawMaterial>(`/api/raw-materials/${id}`, data).then((r) => r.data),
  delete: (id: number) => api.delete(`/api/raw-materials/${id}`),
};

export const productionService = {
  getSuggestion: () =>
    api
      .get<ProductionSuggestion>("/api/production/suggestion")
      .then((r) => r.data),
};

export default api;
