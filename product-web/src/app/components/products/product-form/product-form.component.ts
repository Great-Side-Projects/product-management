// src/app/components/products/product-form/product-form.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="container mt-4">
      <h3>{{ editMode ? 'Edit' : 'Create' }} Product</h3>
      <form [formGroup]="productForm" (ngSubmit)="onSubmit()">
        <div class="mb-3">
          <label for="name" class="form-label">Name</label>
          <input
            type="text"
            class="form-control"
            id="name"
            formControlName="name"
            [ngClass]="{'is-invalid': submitted && f['name'].errors}">
          <div class="invalid-feedback" *ngIf="submitted && f['name'].errors?.['required']">
            Name is required
          </div>
        </div>
        <div class="mb-3">
          <label for="price" class="form-label">Price</label>
          <input
            type="number"
            class="form-control"
            id="price"
            formControlName="price"
            [ngClass]="{'is-invalid': submitted && f['price'].errors}">
          <div class="invalid-feedback" *ngIf="submitted && f['price'].errors?.['required']">
            Price is required
          </div>
        </div>
        <div class="mb-3">
            <label for="quantity" class="form-label">Quantity</label>
            <input
                type="number"
                class="form-control"
                id="quantity"
                formControlName="quantity"
                [ngClass]="{'is-invalid': submitted && f['quantity'].errors}">
            <div class="invalid-feedback" *ngIf="submitted && f['quantity'].errors?.['required']">
                Quantity is required
            </div>
        </div>

        <div class="d-flex gap-2">
          <button type="submit" class="btn btn-primary">Save</button>
          <button type="button" class="btn btn-secondary" (click)="onCancel()">Cancel</button>
        </div>
      </form>
    </div>
  `
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  submitted = false;
  editMode = false;
  productId?: string;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      quantity: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.productId = this.route.snapshot.params['id'];
    if (this.productId) {
      this.editMode = true;
      this.loadProduct();
    }
  }

  get f() { return this.productForm.controls; }

  loadProduct(): void {
    if (this.productId) {
      this.productService.getProduct(this.productId)
        .subscribe({
          next: (responseDTO) => {

            this.productForm.patchValue(responseDTO.data);
          },
          error: (error) => console.error('Error loading product:', error)
        });
    }
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.productForm.invalid) {
      return;
    }

    const product = this.productForm.value;

    if (this.editMode && this.productId) {
      this.productService.updateProduct(this.productId, product)
        .subscribe({
          next: () => this.router.navigate(['/products']),
          error: (error) => console.error('Error updating product:', error)
        });
    } else {
      this.productService.createProduct(product)
        .subscribe({
          next: () => this.router.navigate(['/products']),
          error: (error) => console.error('Error creating product:', error)
        });
    }
  }

  onCancel(): void {
    this.router.navigate(['/products']);
  }
}
