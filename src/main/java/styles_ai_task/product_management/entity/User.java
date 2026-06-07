package styles_ai_task.product_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entity representing an application user.
 *
 * This entity is responsible for storing user
 * authentication and authorization information.
 *
 * Features:
 * - UUID-based primary key
 * - Unique username
 * - Encrypted password storage
 * - Role-based authorization support
 *
 * Example:
 * Username: admin
 * Password: (BCrypt encoded password)
 * Role: EDIT
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Unique identifier of the user.
     *
     * Automatically generated using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Unique username used for authentication.
     *
     * Example:
     * admin
     * john.doe
     *
     * Database constraint:
     * - Must be unique
     */
    @Column(unique = true)
    private String username;

    /**
     * Encrypted user password.
     *
     * Passwords should never be stored in plain text.
     * Typically encoded using BCryptPasswordEncoder.
     *
     * Example:
     * $2a$10$WQ8fK7Q9g2...
     */
    private String password;

    /**
     * User role or permission level.
     *
     * Determines what actions the user
     * is authorized to perform.
     *
     * Example values:
     * VIEW
     * EDIT
     */
    private String role;
}