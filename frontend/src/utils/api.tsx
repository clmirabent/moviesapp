import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL, 
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    if (!config.headers) {
      return config;
    }
    var token = sessionStorage.getItem("token");
    config.headers["Authorization"] = `Bearer ${token}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const isAuthenticated = (): boolean => {
  const token = sessionStorage.getItem("token");
  return !!token;
}

export const clearToken = () => {
  sessionStorage.removeItem("token");
}

export const setAuthToken = (token: string | null) => {
  if (token) {
    api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  } else {
    delete api.defaults.headers.common["Authorization"];
  }
};

export default api;
