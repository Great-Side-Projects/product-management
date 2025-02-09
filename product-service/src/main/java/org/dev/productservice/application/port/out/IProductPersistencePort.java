package org.dev.productservice.application.port.out;

import org.dev.productservice.domain.Product;

import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    Product save(Product product);
    Optional<Product> getProductById(String id);
    void deleteById(String id);
    List<Product> getAllProducts();
    boolean existsById(String id);
}
