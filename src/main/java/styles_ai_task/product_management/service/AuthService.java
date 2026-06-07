package styles_ai_task.product_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import styles_ai_task.product_management.dto.LoginRequest;
import styles_ai_task.product_management.dto.LoginResponse;
import styles_ai_task.product_management.entity.User;
import styles_ai_task.product_management.exception.BusinessException;
import styles_ai_task.product_management.repository.UserRepository;
import styles_ai_task.product_management.security.JwtService;

/**
 * Service responsible for handling authentication operations.
 *
 * This service validates user credentials and generates
 * JWT tokens for successfully authenticated users.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Common error message returned when authentication fails.
     */
    private static final String INVALID_CREDENTIALS =
            "Invalid username or password";

    /**
     * Repository used to retrieve user information from database.
     */
    private final UserRepository userRepository;

    /**
     * Password encoder used for password verification.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT utility service used for token generation.
     */
    private final JwtService jwtService;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * Authentication flow:
     * 1. Find user by username.
     * 2. Verify password against stored encrypted password.
     * 3. Generate JWT token containing username and role.
     * 4. Return token to client.
     *
     * @param request login request containing username and password
     * @return LoginResponse containing JWT token
     * @throws BusinessException when username does not exist
     *                           or password is incorrect
     */
    public LoginResponse login(
            LoginRequest request) {

        // Retrieve user by username
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(
                        "INVALID_CREDENTIALS",
                        INVALID_CREDENTIALS,
                        401));

        // Verify raw password against encrypted password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new BusinessException(
                    "INVALID_CREDENTIALS",
                    INVALID_CREDENTIALS,
                    401);
        }

        // Generate JWT token with username and role
        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole());

        // Return authentication response
        return new LoginResponse(token);
    }
}