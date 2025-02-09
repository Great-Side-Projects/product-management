package org.dev.productservice.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dev.productservice.application.service.ProductService;
import org.dev.productservice.common.WebAdapter;
import org.dev.productservice.domain.Base.ResponseDTO;
import org.dev.productservice.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@WebAdapter
@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Products operations")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Get all products", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of products",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Products not found",
                    content = @Content) })
    @GetMapping
    public ResponseDTO<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get product by id", description = "Get product by id", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Product>> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    public ResponseDTO<Product> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @Operation(summary = "Update a product", description = "Update a product", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Product>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @Operation(summary = "Delete a product", description = "Delete a product", tags = "Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
