package org.dev.productservice;

import org.dev.productservice.application.port.out.IProductPersistencePort;
import org.dev.productservice.application.service.ProductService;
import org.dev.productservice.domain.Base.ResponseDTO;
import org.dev.productservice.domain.Product;
import org.dev.productservice.domain.exceptionhandler.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private IProductPersistencePort productPersistenceAdapter;

    @InjectMocks
    private ProductService productService;

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
        when(productPersistenceAdapter.getAllProducts()).thenReturn(products);

        ResponseDTO<List<Product>> response = productService.getAllProducts();

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(products, response.getData());
    }

    @Test
    void getProductById_returnsProduct_whenProductExists() {
        Product product = new Product(
                "1",
                "Product 1",
                10.0,
                10,
                "Description 1",
                "Image 1"
        );

        when(productPersistenceAdapter.getProductById("1")).thenReturn(Optional.of(product));

        ResponseDTO<Product> response = productService.getProductById("1");

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(product, response.getData());
    }

    @Test
    void getProductById_throwsProductNotFoundException_whenProductDoesNotExist() {
        when(productPersistenceAdapter.getProductById("1")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("1"));
    }

    @Test
    void createProduct_returnsCreatedProduct() {
        Product product = new Product(
                "1",
                "Product 1",
                10.0,
                10,
                "Description 1",
                "Image 1"
        );
        when(productPersistenceAdapter.save(product)).thenReturn(product);

        ResponseDTO<Product> response = productService.createProduct(product);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(product, response.getData());
    }

    @Test
    void updateProduct_returnsUpdatedProduct_whenProductExists() {
        Product product = new Product(
                "1",
                "Product 1",
                10.0,
                10,
                "Description 1",
                "Image 1"
        );

        when(productPersistenceAdapter.existsById("1")).thenReturn(true);
        when(productPersistenceAdapter.save(product)).thenReturn(product);

        ResponseDTO<Product> response = productService.updateProduct("1", product);

        assertNotNull(response);
        assertTrue(response.getSuccess());
        assertEquals(product, response.getData());
    }

    @Test
    void updateProduct_throwsProductNotFoundException_whenProductDoesNotExist() {
        Product product = new Product(
                "1",
                "Product 1",
                10.0,
                10,
                "Description 1",
                "Image 1"
        );
        when(productPersistenceAdapter.existsById("1")).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct("1", product));
    }

    @Test
    void deleteProduct_deletesProduct() {
        doNothing().when(productPersistenceAdapter).deleteById("1");

        productService.deleteProduct("1");

        verify(productPersistenceAdapter, times(1)).deleteById("1");
    }
}