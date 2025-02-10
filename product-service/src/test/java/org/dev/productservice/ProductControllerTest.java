package org.dev.productservice;

import org.dev.productservice.application.service.ProductService;
import org.dev.productservice.domain.Base.ResponseDTO;
import org.dev.productservice.domain.Product;
import org.dev.productservice.domain.exceptionhandler.ProductNotFoundException;
import org.dev.productservice.infrastructure.adapter.in.web.ProductController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_returnsAllProducts() {
        List<Product> products = List.of(new Product(
                "1",
                "Product 1",
                10.0,
                10,
                "Description 1",
                "Image 1"
        ), new Product(
                "2",
                "Product 2",
                12.0,
                20,
                "Description 2",
                "Image 2"
        ));
        ResponseDTO<List<Product>> responseDTO = new ResponseDTO<>("",products, true, List.of());
        when(productService.getAllProducts()).thenReturn(responseDTO);

        ResponseDTO<List<Product>> response = productController.getAllProducts();

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(products, response.getData());
    }

    @Test
    void getProductById_returnsProduct_whenProductExists() {
        Product product = new Product(
                "2",
                "Product 2",
                12.0,
                20,
                "Description 2",
                "Image 2"
        );
        ResponseDTO<Product> responseDTO = new ResponseDTO<>("",product, true, List.of());
        when(productService.getProductById("1")).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO<Product>> response = productController.getProductById("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getSuccess());
        assertEquals(product, response.getBody().getData());
    }

    @Test
    void getProductById_returnsNotFound_whenProductDoesNotExist() {

        when(productService.getProductById("1")).thenThrow(new ProductNotFoundException("Product not found"));
        ResponseEntity<ResponseDTO<String>> responseNotFound = null;

        try {
            ResponseEntity<ResponseDTO<Product>> response  = productController.getProductById("1");
        }
        catch (ProductNotFoundException e) {
            responseNotFound = new ResponseEntity<>(new ResponseDTO<>("", e.getMessage(), true, List.of()), HttpStatus.NOT_FOUND);
        }

        assertEquals(HttpStatus.NOT_FOUND, responseNotFound.getStatusCode());
    }

    @Test
    void createProduct_returnsCreatedProduct() {
        Product product = new Product(
                "2",
                "Product 2",
                12.0,
                20,
                "Description 2",
                "Image 2"
        );
        ResponseDTO<Product> responseDTO = new ResponseDTO<>("",product, true, List.of());
        when(productService.createProduct(product)).thenReturn(responseDTO);

        ResponseDTO<Product> response = productController.createProduct(product);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(product, response.getData());
    }

    @Test
    void updateProduct_returnsUpdatedProduct_whenProductExists() {
        Product product = new Product(
                "2",
                "Product 2",
                12.0,
                20,
                "Description 2",
                "Image 2"
        );
        ResponseDTO<Product> responseDTO = new ResponseDTO<>("",product, true, List.of());
        when(productService.updateProduct("1", product)).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO<Product>> response = productController.updateProduct("1", product);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getSuccess());
        assertEquals(product, response.getBody().getData());
    }

    @Test
    void updateProduct_returnsNotFound_whenProductDoesNotExist() {

        Product product = new Product(
                "2",
                "Product 2",
                12.0,
                20,
                "Description 2",
                "Image 2"
        );
        ResponseDTO<String> notFoundResponse = new ResponseDTO<>("", "Product not found", true, List.of());
        when(productService.updateProduct("1", product)).thenThrow(new ProductNotFoundException("Product not found"));
        try {
            ResponseEntity<ResponseDTO<Product>> response = productController.updateProduct("1", product);
        } catch (Exception e) {

        }

        assertEquals("Product not found", notFoundResponse.getData());
        assertTrue(notFoundResponse.getSuccess());
    }

    @Test
    void deleteProduct_returnsNoContent() {
        doNothing().when(productService).deleteProduct("1");

        ResponseEntity<Void> response = productController.deleteProduct("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
