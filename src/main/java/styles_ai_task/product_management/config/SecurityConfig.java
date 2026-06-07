package styles_ai_task.product_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import styles_ai_task.product_management.security.JwtAuthenticationFilter;

/**
 * Security configuration for the application.
 *
 * Responsibilities:
 * - Configure authentication and authorization rules.
 * - Register security filters.
 * - Enable JWT-based authentication.
 * - Disable server-side sessions for stateless REST APIs.
 * - Expose PasswordEncoder bean for password hashing.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Custom JWT filter responsible for:
     * - Extracting JWT token from incoming requests.
     * - Validating the token.
     * - Setting authenticated user details in SecurityContext.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Password encoder bean used during:
     * - User registration (password hashing)
     * - User login (password verification)
     *
     * BCrypt is recommended because it automatically applies salting
     * and is resistant to brute-force attacks.
     *
     * @return BCryptPasswordEncoder implementation
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the application's security filter chain.
     *
     * Security Flow:
     * 1. Disable CSRF since this is a stateless REST API.
     * 2. Disable HTTP session creation.
     * 3. Allow unauthenticated access to authentication endpoints.
     * 4. Require authentication for all other endpoints.
     * 5. Execute JWT filter before Spring's default authentication filter.
     *
     * @param http HttpSecurity configuration object
     * @return configured SecurityFilterChain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * CSRF protection is primarily required for browser-based
                 * applications that use cookies for authentication.
                 *
                 * Since this application uses JWT tokens and is stateless,
                 * CSRF protection is disabled.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Instruct Spring Security not to create or use HTTP sessions.
                 *
                 * Every request must contain a valid JWT token because
                 * authentication information is not stored on the server.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))

                /*
                 * Authorization rules:
                 *
                 * /auth/** endpoints:
                 *     Accessible without authentication.
                 *     Examples:
                 *     - POST /auth/register
                 *     - POST /auth/login
                 *
                 * All remaining endpoints require authentication.
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )

                /*
                 * Register JWT authentication filter before Spring's
                 * UsernamePasswordAuthenticationFilter.
                 *
                 * This ensures JWT validation happens first and the
                 * SecurityContext is populated before authorization checks.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                /*
                 * Enables default HTTP Basic authentication support.
                 *
                 * Optional when JWT authentication is the primary mechanism.
                 * Can be removed if Basic Authentication is not required.
                 */
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}