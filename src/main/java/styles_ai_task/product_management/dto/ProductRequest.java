package styles_ai_task.product_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO used to receive product information from the client.
 *
 * This request object is used during product creation
 * and product update operations.
 *
 * Validation annotations ensure that all required
 * product details are provided and contain valid values.
 *
 * Example Request:
 * {
 *   "name": "Male Shirt",
 *   "description": "Cotton casual shirt",
 *   "price": 999.99,
 *   "quantity": 10,
 *   "imageUrl": "https://example.com/shirt.jpg"
 * }
 */
@Data
public class ProductRequest {

    /**
     * Product name.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be empty
     * - Cannot contain only whitespace
     */
    @NotBlank(message = "Product name is required")
    private String name;

    /**
     * Product description.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be empty
     * - Cannot contain only whitespace
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     * Product price.
     *
     * Validation:
     * - Cannot be null
     * - Must be greater than zero
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    /**
     * Available product quantity.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be negative
     * - Zero quantity is allowed
     */
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    /**
     * Product image URL.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be empty
     * - Cannot contain only whitespace
     */
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}