package org.dev.productservice.application.service;

import lombok.RequiredArgsConstructor;
import org.dev.productservice.application.port.out.IProductPersistencePort;
import org.dev.productservice.common.UseCase;
import org.dev.productservice.domain.Base.ResponseDTO;
import org.dev.productservice.domain.Product;
import org.dev.productservice.domain.exceptionhandler.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class ProductService {

    private final IProductPersistencePort productPersistenceAdapter;

    public ResponseDTO<List<Product>> getAllProducts() {

        return new ResponseDTO<>(
                null,
                productPersistenceAdapter.getAllProducts(),
                true,
                List.of()
        );
    }

    public ResponseDTO<Product> getProductById(String id) {

        Optional<Product> product = productPersistenceAdapter.getProductById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        return new ResponseDTO<>(null, product.get(), true, List.of());
    }

    public ResponseDTO<Product> createProduct(Product product) {

        return new ResponseDTO<>(null, productPersistenceAdapter.save(product), true, List.of());
    }

    public ResponseDTO<Product> updateProduct(String id, Product product) {
        if (!productPersistenceAdapter.existsById(id)) {
            throw new ProductNotFoundException("Product not found");
        }
        product.setId(id);

        return new ResponseDTO<>(null, productPersistenceAdapter.save(product), true, List.of());
    }

    public void deleteProduct(String id) {
        productPersistenceAdapter.deleteById(id);
    }
}
