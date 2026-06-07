package styles_ai_task.product_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT Authentication Filter.
 *
 * This filter executes once for every incoming HTTP request
 * and is responsible for:
 *
 * 1. Reading the JWT token from the Authorization header.
 * 2. Validating and parsing the token.
 * 3. Extracting user information (username and role).
 * 4. Creating an authenticated SecurityContext.
 * 5. Allowing Spring Security to authorize requests based on roles.
 *
 * Example Header:
 *
 * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
 *
 * Flow:
 *
 * Client Request
 *       |
 *       v
 * JwtAuthenticationFilter
 *       |
 *       v
 * Extract JWT
 *       |
 *       v
 * Validate Token
 *       |
 *       v
 * Extract Username & Role
 *       |
 *       v
 * Set Authentication
 *       |
 *       v
 * Controller Access
 *
 * Since this application is stateless, authentication
 * information is reconstructed from the JWT token on
 * every request.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Service responsible for JWT operations such as:
     * - Extracting username
     * - Extracting role
     * - Validating token
     */
    private final JwtService jwtService;

    /**
     * Executes once for every incoming request.
     *
     * Steps:
     * 1. Read Authorization header.
     * 2. Verify Bearer token exists.
     * 3. Extract JWT token.
     * 4. Read username and role from token.
     * 5. Create Authentication object.
     * 6. Store authentication in SecurityContext.
     * 7. Continue filter chain.
     *
     * @param request incoming HTTP request
     * @param response outgoing HTTP response
     * @param filterChain remaining filters in chain
     * @throws ServletException servlet exception
     * @throws IOException I/O exception
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        /**
         * Read Authorization header.
         *
         * Example:
         * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
         */
        String authHeader = request.getHeader("Authorization");

        /**
         * Skip authentication if:
         * - Header is missing
         * - Header does not start with "Bearer "
         *
         * Request continues without authentication.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        /**
         * Remove "Bearer " prefix and extract JWT token.
         */
        String token = authHeader.substring(7);

        try {

            /**
             * Extract username from JWT payload.
             *
             * Example:
             * admin
             */
            String username = jwtService.extractUsername(token);

            /**
             * Extract role/authority from JWT payload.
             *
             * Example:
             * VIEW
             * EDIT
             */
            String role = jwtService.extractRole(token);

            /**
             * Create authenticated user object.
             *
             * principal    -> username
             * credentials  -> null (password not required)
             * authorities  -> user role
             */
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(
                                    new SimpleGrantedAuthority(role)
                            )
                    );

            /**
             * Store authentication information inside
             * Spring Security Context.
             *
             * After this point:
             *
             * @PreAuthorize(...)
             * SecurityContextHolder
             * Authentication
             *
             * can access the authenticated user.
             */
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception ignored) {

            /**
             * Invalid or expired token.
             *
             * Authentication is not set.
             * Request proceeds as anonymous user.
             *
             * Protected endpoints will later return:
             * 401 Unauthorized
             * or
             * 403 Forbidden
             */
        }

        /**
         * Continue processing remaining filters
         * and eventually reach the controller.
         */
        filterChain.doFilter(request, response);
    }
}