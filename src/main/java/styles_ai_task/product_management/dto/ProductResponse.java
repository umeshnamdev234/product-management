package styles_ai_task.product_management.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Response DTO used to return product information
 * to the client.
 *
 * This object represents the API response for
 * product-related operations such as:
 * - Get all products
 * - Get product by ID
 * - Create product
 * - Update product
 *
 * Example Response:
 * {
 * "id": "550e8400-e29b-41d4-a716-446655440000",
 * "name": "Male Shirt",
 * "description": "Cotton casual shirt",
 * "price": 999.99,
 * "quantity": 10,
 * "imageUrl": "https://example.com/shirt.jpg"
 * }
 */
@Data
@Builder
public class ProductResponse {

    /**
     * Unique identifier of the product.
     */
    private UUID id;

    /**
     * Product name.
     */
    private String name;

    /**
     * Product description.
     */
    private String description;

    /**
     * Product price.
     */
    private BigDecimal price;

    /**
     * Available stock quantity.
     */
    private Integer quantity;

    /**
     * Product image URL.
     */
    private String imageUrl;
}