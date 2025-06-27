
export interface LoginForm {
  email: string;
  password: string;
}


export interface RegisterForm {
  userName: string;
  email: string;
  password: string;
}


export interface SearchForm {
  query: string;
}

export interface User {
  id: string;
  name: string;
  email: string;
}

 export interface Session {
token: string;
  user: { [key: string]: any; password?: string };
}