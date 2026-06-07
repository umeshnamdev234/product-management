package styles_ai_task.product_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO returned after successful authentication.
 *
 * This response contains the JWT token generated
 * by the application, which will be used by the
 * client for accessing secured endpoints.
 *
 * Example Response:
 * {
 *   "token": "eyJhbGciOiJIUzI1NiJ9..."
 * }
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    /**
     * JWT token generated after successful login.
     *
     * The client should include this token in the
     * Authorization header for protected API requests.
     *
     * Example:
     * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
     */
    private String token;
}