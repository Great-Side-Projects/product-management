// src/app/components/products/product-list/product-list.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Product } from '../../../models/product';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="container mt-4">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Products Management </h2>
        <a [routerLink]="['/products/new']" class="btn btn-primary">Add Product</a>
      </div>

      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>Name</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let product of products">
              <td>{{product.name}}</td>
              <td>{{product.price | currency}}</td>
              <td>{{product.quantity}}</td>
              <td>
                <a [routerLink]="['/products/edit', product.id]" class="btn btn-sm btn-info me-2">Edit</a>
                <button class="btn btn-sm btn-danger" (click)="deleteProduct(product.id!)">Delete</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts()
      .subscribe({
        next: (products) => this.products = products.data,
        error: (error) => console.error('Error loading products:', error)
      });
  }

  deleteProduct(id: string): void {
    if(confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id)
        .subscribe({
          next: () => {
            this.products = this.products.filter(product => product.id !== id);
          },
          error: (error) => console.error('Error deleting product:', error)
        });
    }
  }
}
