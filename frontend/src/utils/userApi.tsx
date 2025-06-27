import type { LoginForm, RegisterForm } from "../types";
import axios from "axios";

export interface Session {
  token: string;
  user: {
    id: number;
    name: string;
    email: string;
  };
}

console.log("API URL:", import.meta.env.VITE_API_URL);

const unAuthApi = axios.create({
  baseURL: import.meta.env.VITE_API_URL, 
  headers: {
    "Content-Type": "application/json",
  },
});

export function loginUser(form: LoginForm): Promise<Session> {
  return Promise.resolve(unAuthApi.post<Session>("/auth/login", form)).then((res) => res.data);
}

export function registerUser(form: RegisterForm): Promise<Session> {
  return Promise.resolve(unAuthApi.post<Session>("/auth/register", form)).then((res) => res.data);
}






