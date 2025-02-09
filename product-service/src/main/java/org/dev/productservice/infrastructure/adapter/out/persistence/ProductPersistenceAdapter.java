package org.dev.productservice.infrastructure.adapter.out.persistence;

import org.dev.productservice.application.port.out.IProductPersistencePort;
import org.dev.productservice.application.port.out.IProductRepository;
import org.dev.productservice.common.PersistenceAdapter;
import org.dev.productservice.domain.Product;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
public class ProductPersistenceAdapter implements IProductPersistencePort {

    private final IProductRepository productRepository;

    public ProductPersistenceAdapter(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        entity = productRepository.save(entity);
        product.setId(entity.getId());
        return product;
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }
}
