package styles_ai_task.product_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity representing a product in the system.
 *
 * This class is mapped to the 'products' database table
 * and stores all product-related information.
 *
 * Features:
 * - UUID-based primary key
 * - Unique product name constraint
 * - Product inventory management
 * - Product pricing information
 * - Product image reference
 */
@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_name", columnNames = "name")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    /**
     * Unique identifier of the product.
     *
     * Automatically generated using UUID strategy.
     *
     * Example:
     * 550e8400-e29b-41d4-a716-446655440000
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer quantity;

    /**
     * URL of the product image.
     *
     * Stores the location of the product image
     * that can be displayed in client applications.
     *
     * Cannot be null.
     */
    @Column(nullable = false)
    private String imageUrl;
}