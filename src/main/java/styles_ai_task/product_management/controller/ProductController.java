package styles_ai_task.product_management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import styles_ai_task.product_management.dto.ProductRequest;
import styles_ai_task.product_management.entity.Product;
import styles_ai_task.product_management.service.ProductService;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller responsible for Product Management operations.
 *
 * Base URL:
 * /products
 *
 * Supported Operations:
 * - Get all products
 * - Get product by ID
 * - Create product
 * - Update product
 * - Delete product
 *
 * Security:
 * - VIEW authority can read products
 * - EDIT authority can create, update, and delete products
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    /**
     * Service layer responsible for handling
     * product-related business logic.
     */
    private final ProductService productService;

    /**
     * Fetches all available products.
     *
     * Endpoint:
     * GET /products
     *
     * Authorization:
     * Requires VIEW or EDIT authority.
     *
     * @return List of all products.
     */
    @PreAuthorize("hasAuthority('VIEW') or hasAuthority('EDIT')")
    @GetMapping
    public List<Product> getProducts() {

        return productService.getAllProducts();
    }

    /**
     * Fetches a single product by its unique identifier.
     *
     * Endpoint:
     * GET /products/{id}
     *
     * Authorization:
     * Requires VIEW or EDIT authority.
     *
     * Example:
     * GET /products/550e8400-e29b-41d4-a716-446655440000
     *
     * @param id Unique product identifier.
     * @return Product details.
     */
    @PreAuthorize("hasAuthority('VIEW') or hasAuthority('EDIT')")
    @GetMapping("/{id}")
    public Product getProduct(
            @PathVariable UUID id) {

        return productService.getProduct(id);
    }

    /**
     * Creates a new product.
     *
     * Endpoint:
     * POST /products
     *
     * Authorization:
     * Requires EDIT authority.
     *
     * Validation:
     * Request body is validated using Bean Validation annotations
     * defined inside ProductRequest.
     *
     * Sample Request:
     * {
     *   "name": "Male Shirt",
     *   "description": "Cotton shirt",
     *   "price": 999.99,
     *   "quantity": 10,
     *   "imageUrl": "https://example.com/shirt.jpg"
     * }
     *
     * @param request Product creation request.
     * @return Newly created product.
     */
    @PreAuthorize("hasAuthority('EDIT')")
    @PostMapping
    public Product createProduct(
            @Valid @RequestBody ProductRequest request) {

        return productService.createProduct(request);
    }

    /**
     * Updates an existing product.
     *
     * Endpoint:
     * PUT /products/{id}
     *
     * Authorization:
     * Requires EDIT authority.
     *
     * Validation:
     * Request body is validated before processing.
     *
     * @param id Product identifier.
     * @param request Updated product information.
     * @return Updated product.
     */
    @PreAuthorize("hasAuthority('EDIT')")
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {

        return productService.updateProduct(id, request);
    }

    /**
     * Deletes a product by its identifier.
     *
     * Endpoint:
     * DELETE /products/{id}
     *
     * Authorization:
     * Requires EDIT authority.
     *
     * Example:
     * DELETE /products/550e8400-e29b-41d4-a716-446655440000
     *
     * @param id Product identifier.
     */
    @PreAuthorize("hasAuthority('EDIT')")
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable UUID id) {

        productService.deleteProduct(id);
    }
}