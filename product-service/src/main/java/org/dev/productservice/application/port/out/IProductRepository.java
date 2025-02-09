package org.dev.productservice.application.port.out;

import org.dev.productservice.infrastructure.adapter.out.persistence.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends MongoRepository<ProductEntity, String> {
}
