export interface Product {
  id?: string;
  name: string;
  price: number;
  quantity: string;
  description: string;
  imageUrl: string;
}

export interface ResponseDTO<T> {
  error: string;
  data: T;
  success: boolean;
  messages: string[];
}
