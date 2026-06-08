package styles_ai_task.product_management.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import styles_ai_task.product_management.dto.LoginRequest;
import styles_ai_task.product_management.entity.User;
import styles_ai_task.product_management.exception.BusinessException;
import styles_ai_task.product_management.repository.UserRepository;
import styles_ai_task.product_management.security.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService.
 *
 * This test class verifies authentication-related
 * business logic without requiring a database or
 * JWT implementation.
 *
 * Dependencies are mocked using Mockito to ensure
 * that only AuthService behavior is tested.
 *
 * Scenarios covered:
 * - Successful login
 * - Invalid username
 * - Invalid credentials
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    /**
     * Mocked repository used to simulate
     * user retrieval from database.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Mocked password encoder used to
     * verify password validation logic.
     */
    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * Mocked JWT service used to simulate
     * token generation.
     */
    @Mock
    private JwtService jwtService;

    /**
     * Service under test.
     *
     * Mockito automatically injects
     * mocked dependencies into AuthService.
     */
    @InjectMocks
    private AuthService authService;

    /**
     * Verifies successful login flow.
     *
     * Steps:
     * 1. Find user by username.
     * 2. Validate password.
     * 3. Generate JWT token.
     * 4. Return LoginResponse.
     *
     * Expected Result:
     * - Token is returned successfully.
     */
    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("admin123");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");
        user.setRole("EDIT");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "admin123",
                "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken(
                "admin",
                "EDIT"))
                .thenReturn("jwt-token");

        var response = authService.login(request);

        assertEquals(
                "jwt-token",
                response.getToken());

        verify(userRepository, times(1))
                .findByUsername("admin");

        verify(passwordEncoder, times(1))
                .matches("admin123", "encodedPassword");

        verify(jwtService, times(1))
                .generateToken("admin", "EDIT");
    }

    /**
     * Verifies behavior when the provided
     * username does not exist.
     *
     * Expected Result:
     * - BusinessException is thrown.
     * - JWT token is not generated.
     */
    @Test
    void shouldThrowExceptionForInvalidUser() {

        LoginRequest request = new LoginRequest();
        request.setUsername("invalid");

        when(userRepository.findByUsername("invalid"))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> authService.login(request));

        assertEquals(
                "INVALID_CREDENTIALS",
                exception.getErrorCode());

        verify(jwtService, never())
                .generateToken(anyString(), anyString());
    }

    /**
     * Verifies behavior when username exists
     * but password is incorrect.
     *
     * Expected Result:
     * - BusinessException is thrown.
     * - JWT token is not generated.
     */
    @Test
    void shouldThrowExceptionForInvalidPassword() {

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("wrong-password");

        User user = new User();
        user.setUsername("admin");
        user.setPassword("encodedPassword");
        user.setRole("EDIT");

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "wrong-password",
                "encodedPassword"))
                .thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> authService.login(request));

        assertEquals(
                "INVALID_CREDENTIALS",
                exception.getErrorCode());

        verify(jwtService, never())
                .generateToken(anyString(), anyString());
    }
}