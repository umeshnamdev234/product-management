package styles_ai_task.product_management.repository;

import styles_ai_task.product_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for User entity.
 *
 * This repository is responsible for performing
 * database operations related to application users.
 *
 * By extending JpaRepository, Spring Data JPA
 * automatically provides implementations for
 * common CRUD operations without requiring
 * manual SQL queries.
 *
 * Supported Operations:
 * - Create user
 * - Update user
 * - Delete user
 * - Find user by ID
 * - Retrieve all users
 * - Count users
 *
 * Entity  : User
 * Primary Key Type : UUID
 *
 * Example:
 *
 * userRepository.save(user);
 * userRepository.findById(id);
 * userRepository.findAll();
 * userRepository.deleteById(id);
 */
public interface UserRepository
        extends JpaRepository<User, UUID> {

    /**
     * Finds a user by username.
     *
     * Spring Data JPA automatically generates
     * the required query based on the method name.
     *
     * Generated SQL (conceptually):
     *
     * SELECT *
     * FROM users
     * WHERE username = ?
     *
     * This method returns Optional<User> to safely
     * handle cases where no user exists with the
     * provided username.
     *
     * Common Use Cases:
     * - User login/authentication
     * - JWT authentication
     * - User lookup during authorization
     *
     * Example:
     *
     * Optional<User> user =
     *         userRepository.findByUsername("admin");
     *
     * @param username unique username
     * @return Optional containing User if found,
     *         otherwise Optional.empty()
     */
    Optional<User> findByUsername(String username);
}