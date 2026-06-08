package styles_ai_task.product_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import styles_ai_task.product_management.dto.ProductRequest;
import styles_ai_task.product_management.entity.Product;
import styles_ai_task.product_management.exception.GlobalExceptionHandler;
import styles_ai_task.product_management.security.JwtService;
import styles_ai_task.product_management.service.ProductService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for ProductController.
 *
 * This test class verifies:
 * - Successful product creation
 * - Validation failures
 * - Invalid request payload handling
 * - Controller behavior without loading the full application context
 *
 * @WebMvcTest loads only MVC-related components
 *             such as Controllers, Jackson, Validation, etc.
 *
 *             ProductService and JwtService are mocked to isolate
 *             controller behavior from external dependencies.
 */
@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    /**
     * MockMvc is used to perform HTTP requests
     * against controller endpoints without starting
     * an actual web server.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Used for converting Java objects
     * into JSON request bodies.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Mocked ProductService.
     *
     * Prevents database interaction and allows
     * controller behavior to be tested independently.
     */
    @MockitoBean
    private ProductService productService;

    /**
     * Mocked JwtService.
     *
     * Required because ProductController is protected
     * by Spring Security and JwtAuthenticationFilter.
     */
    @MockitoBean
    private JwtService jwtService;

    /**
     * Verifies that a valid product request
     * successfully creates a product.
     *
     * Expected Result:
     * - HTTP 200 OK
     * - Product JSON returned
     * - ProductService invoked once
     */
    @Test
    void shouldCreateProductSuccessfully() throws Exception {

        ProductRequest request = new ProductRequest();
        request.setName("Male Shirt");
        request.setDescription("Test Description");
        request.setPrice(BigDecimal.valueOf(100));
        request.setQuantity(10);
        request.setImageUrl("https://test.com/image.jpg");

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl())
                .build();

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(product);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Male Shirt"))
                .andExpect(jsonPath("$.description")
                        .value("Test Description"))
                .andExpect(jsonPath("$.price").value(100))
                .andExpect(jsonPath("$.quantity").value(10));

        verify(productService, times(1))
                .createProduct(any(ProductRequest.class));
    }

    /**
     * Verifies validation failure when
     * product name is missing.
     *
     * Expected Result:
     * - HTTP 400 Bad Request
     * - Validation error response
     */
    @Test
    void shouldReturnBadRequestWhenNameMissing() throws Exception {

        String request = """
                {
                    "description":"Test",
                    "price":100,
                    "quantity":10,
                    "imageUrl":"https://test.com/image.jpg"
                }
                """;

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").exists());
    }

    /**
     * Verifies behavior when request body
     * is completely missing.
     *
     * Expected Result:
     * - HTTP 400 Bad Request
     */
    @Test
    void shouldReturnBadRequestWhenBodyMissing() throws Exception {

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies validation when incorrect
     * data types are supplied.
     *
     * Example:
     * price should be numeric but receives string.
     *
     * Expected Result:
     * - HTTP 400 Bad Request
     * - Error response generated by
     * GlobalExceptionHandler
     */
    @Test
    void shouldReturnBadRequestWhenInvalidDataType() throws Exception {

        String request = """
                {
                    "name":"Male Shirt",
                    "description":"Test",
                    "price":"Rs.100",
                    "quantity":"Rs.10",
                    "imageUrl":"https://test.com/image.jpg"
                }
                """;

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").exists());
    }
}