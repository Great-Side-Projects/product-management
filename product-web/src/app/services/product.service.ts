// src/app/services/product.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Product, ResponseDTO} from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8084/products'; // URL del API Gateway

  constructor(private http: HttpClient) { }

  getProducts(): Observable<ResponseDTO<Product[]>> {
    return this.http.get<ResponseDTO<Product[]>>(this.apiUrl);
  }

  getProduct(id: string): Observable<ResponseDTO<Product>> {
    return this.http.get<ResponseDTO<Product>>(`${this.apiUrl}/${id}`);
  }

  createProduct(product: Product): Observable<ResponseDTO<Product>> {
    return this.http.post<ResponseDTO<Product>>(this.apiUrl, product);
  }

  updateProduct(id: string, product: Product): Observable<ResponseDTO<Product>> {
    return this.http.put<ResponseDTO<Product>>(`${this.apiUrl}/${id}`, product);
  }

  deleteProduct(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
