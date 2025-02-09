package org.dev.productservice.infrastructure.adapter.out.persistence;

import org.dev.productservice.domain.Product;

public class ProductMapper {

   public static Product toDomain(ProductEntity entity)
    {
        return new Product(entity.getId(), entity.getName(), entity.getPrice(), entity.getQuantity(), entity.getDescription(), entity.getImage());
    }

    public static ProductEntity toEntity(Product domain)
    {
        return new ProductEntity(domain.getId(), domain.getName(), domain.getPrice(), domain.getQuantity(), domain.getDescription(), domain.getImage());
    }
}
