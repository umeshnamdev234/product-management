package styles_ai_task.product_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO used for user authentication requests.
 *
 * This object receives login credentials from the client
 * and is validated before authentication is performed.
 *
 * Example Request:
 * {
 *   "username": "admin",
 *   "password": "password123"
 * }
 */
@Data
public class LoginRequest {

    /**
     * Username of the user attempting to log in.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be empty
     * - Cannot contain only whitespace
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * Password of the user attempting to log in.
     *
     * Validation:
     * - Cannot be null
     * - Cannot be empty
     * - Cannot contain only whitespace
     */
    @NotBlank(message = "Password is required")
    private String password;
}