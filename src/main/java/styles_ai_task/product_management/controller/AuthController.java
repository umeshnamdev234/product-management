package styles_ai_task.product_management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import styles_ai_task.product_management.dto.LoginRequest;
import styles_ai_task.product_management.dto.LoginResponse;
import styles_ai_task.product_management.service.AuthService;

/**
 * REST Controller responsible for authentication operations.
 *
 * Base URL:
 * /auth
 *
 * Currently supported endpoints:
 * - POST /auth/login
 *
 * This controller delegates authentication logic to AuthService
 * and returns authentication responses to clients.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    /**
     * Service responsible for validating user credentials
     * and generating authentication responses.
     */
    private final AuthService authService;

    /**
     * Authenticates a user and returns a login response.
     *
     * Endpoint:
     * POST /auth/login
     *
     * Sample Request:
     * {
     *   "email": "user@example.com",
     *   "password": "password123"
     * }
     *
     * Sample Response:
     * {
     *   "token": "jwt-token"
     * }
     *
     * @param request Login request containing user credentials.
     *                Validation is performed automatically using
     *                Jakarta Bean Validation annotations defined
     *                in LoginRequest.
     *
     * @return LoginResponse containing JWT token or authentication details.
     */
    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}