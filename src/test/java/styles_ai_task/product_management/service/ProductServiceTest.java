package styles_ai_task.product_management.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import styles_ai_task.product_management.entity.Product;
import styles_ai_task.product_management.exception.BusinessException;
import styles_ai_task.product_management.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 
 * Unit tests for ProductService.
 *
 * These tests verify the business logic of product retrieval
 * without requiring a real database connection.
 *
 * Mockito is used to mock ProductRepository and isolate
 * ProductService behavior.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    /**
     * 
     * Mocked repository dependency.
     *
     * Prevents actual database interaction during tests.
     */
    @Mock
    private ProductRepository productRepository;

    /**
     * 
     * Service under test.
     *
     * Mock dependencies are automatically injected
     * into this instance by Mockito.
     */
    @InjectMocks
    private ProductService productService;

    /**
     * 
     * Verifies that a product is returned successfully
     * when a valid product ID exists in the repository.
     *
     * Expected Result:
     * * Repository returns a product.
     * * Service returns the same product.
     * * Product name matches the expected value.
     */
    @Test
    void shouldGetProductSuccessfully() {

        UUID id = UUID.randomUUID();

        Product product = Product.builder()
                .id(id)
                .name("Shirt")
                .build();

        when(productRepository.findById(id))
                .thenReturn(Optional.of(product));

        Product result = productService.getProduct(id);

        assertEquals("Shirt", result.getName());
    }

    /**
     * 
     * Verifies that BusinessException is thrown
     * when the requested product does not exist.
     *
     * Expected Result:
     * * Repository returns empty result.
     * * Service throws BusinessException.
     * * Error code equals PRODUCT_NOT_FOUND.
     */
    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        UUID id = UUID.randomUUID();

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> productService.getProduct(id));

        assertEquals(
                "PRODUCT_NOT_FOUND",
                exception.getErrorCode());
    }
}
