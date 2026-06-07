package styles_ai_task.product_management.repository;

import styles_ai_task.product_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for Product entity.
 *
 * Spring Data JPA automatically provides implementations
 * for common database operations such as:
 *
 * - Save product
 * - Update product
 * - Delete product
 * - Find product by ID
 * - Retrieve all products
 * - Count records
 *
 * By extending JpaRepository, we avoid writing
 * boilerplate SQL or DAO implementation code.
 *
 * Entity  : Product
 * Primary Key Type : UUID
 *
 * Example:
 * productRepository.save(product);
 * productRepository.findById(id);
 * productRepository.findAll();
 * productRepository.deleteById(id);
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Checks whether a product already exists
     * with the given name.
     *
     * Spring Data JPA automatically generates
     * the query based on the method name.
     *
     * Generated SQL (conceptually):
     *
     * SELECT COUNT(*)
     * FROM products
     * WHERE name = ?
     *
     * Use Cases:
     * - Prevent duplicate product creation
     * - Validate unique product names
     *
     * Example:
     *
     * boolean exists =
     *         productRepository.existsByName("Male Shirt");
     *
     * @param name product name to check
     * @return true if product exists, otherwise false
     */
    boolean existsByName(String name);
}