export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  phone: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface UserDto {
  id: string;
  name: string;
  email: string;
  phone: string;
}
