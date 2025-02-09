package org.dev.productservice.infrastructure.adapter.out.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class ProductEntity {

    public ProductEntity(String id, String name, double price, int quantity, String description, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
    }

    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String image;
}
