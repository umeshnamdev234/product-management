package styles_ai_task.product_management.service;

import styles_ai_task.product_management.dto.ProductRequest;
import styles_ai_task.product_management.entity.Product;
import styles_ai_task.product_management.exception.BusinessException;
import styles_ai_task.product_management.repository.ProductRepository;
import styles_ai_task.product_management.exception.ErrorCodes;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import styles_ai_task.product_management.dto.PaginatedResponse;

//import java.util.List;
import java.util.UUID;

/**
 * Service responsible for managing product-related
 * business operations.
 *
 * This service acts as a bridge between the controller
 * layer and the repository layer.
 *
 * Responsibilities:
 * - Retrieve products
 * - Create products
 * - Update products
 * - Delete products
 * - Handle product-related business validations
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    /**
     * Repository used for performing database
     * operations on Product entities.
     */
    private final ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     *
     * @return list of all available products
     */
    public PaginatedResponse<Product> getAllProducts(
            int pageNumber,
            int pageSize) {

        Pageable pageable = PageRequest.of(
                Math.max(pageNumber - 1, 0),
                pageSize);

        Page<Product> page = productRepository.findAll(pageable);

        return PaginatedResponse.<Product>builder()
                .data(page.getContent())
                .totalRecords(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
    }

    /**
     * Retrieves a product by its unique identifier.
     *
     * If no product exists with the provided ID,
     * a BusinessException is thrown.
     *
     * @param id unique product identifier
     * @return product details
     * @throws BusinessException when product is not found
     */
    public Product getProduct(UUID id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCodes.PRODUCT_NOT_FOUND,
                        "Product not found",
                        404));
    }

    /**
     * Creates a new product.
     *
     * Converts ProductRequest DTO into Product entity
     * and persists it into the database.
     *
     * @param request product creation request
     * @return saved product entity
     */
    public Product createProduct(ProductRequest request) {

        // Build Product entity from request payload
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .imageUrl(request.getImageUrl())
                .build();

        // Save product into database
        return productRepository.save(product);
    }

    /**
     * Updates an existing product.
     *
     * Steps:
     * 1. Validate product existence.
     * 2. Update product fields.
     * 3. Save updated product.
     *
     * @param id      product identifier
     * @param request updated product data
     * @return updated product entity
     * @throws BusinessException when product does not exist
     */
    public Product updateProduct(
            UUID id,
            ProductRequest request) {

        // Fetch existing product or throw exception
        Product product = getProduct(id);

        // Update product properties
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setImageUrl(request.getImageUrl());

        // Persist updated product
        return productRepository.save(product);
    }

    /**
     * Deletes a product by ID.
     *
     * If the product does not exist, Spring Data JPA
     * silently ignores the delete operation.
     *
     * @param id product identifier
     */
    public void deleteProduct(UUID id) {

        productRepository.deleteById(id);
    }
}