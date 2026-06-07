package styles_ai_task.product_management.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import styles_ai_task.product_management.security.JwtAuthenticationFilter;
import styles_ai_task.product_management.security.JwtService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for GlobalExceptionHandler.
 *
 * This test class verifies that custom exceptions are
 * correctly translated into standardized API responses.
 *
 * Scenarios covered:
 * - BusinessException
 * - AuthorizationDeniedException
 * - Generic RuntimeException
 *
 * A lightweight test controller is created specifically
 * to trigger exceptions and validate handler behavior.
 */
@WebMvcTest(
        controllers = GlobalExceptionHandlerTest.TestController.class
)
@Import({
        GlobalExceptionHandler.class,
        GlobalExceptionHandlerTest.TestController.class
})
@AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {

    /**
     * MockMvc allows execution of HTTP requests
     * without starting an actual web server.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mocked security filter.
     *
     * Required because the application uses JWT-based
     * authentication and the filter would otherwise
     * be loaded into the test context.
     */
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Mocked JWT service dependency.
     */
    @MockitoBean
    private JwtService jwtService;

    /**
     * Test controller used only for exception testing.
     *
     * Each endpoint intentionally throws a specific
     * exception so that GlobalExceptionHandler can
     * be validated independently.
     */
    @RestController
    static class TestController {

        /**
         * Endpoint that throws BusinessException.
         *
         * Expected Response:
         * HTTP 404 Not Found
         */
        @GetMapping("/business")
        public String businessException() {

            throw new BusinessException(
                    "PRODUCT_NOT_FOUND",
                    "Product not found",
                    HttpStatus.NOT_FOUND.value()
            );
        }

        /**
         * Endpoint that throws AuthorizationDeniedException.
         *
         * Expected Response:
         * HTTP 403 Forbidden
         */
        @GetMapping("/access-denied")
        public String accessDenied() {

            throw new AuthorizationDeniedException(
                    "Access Denied"
            );
        }

        /**
         * Endpoint that throws an unexpected exception.
         *
         * Expected Response:
         * HTTP 500 Internal Server Error
         */
        @GetMapping("/internal-error")
        public String internalError() {

            throw new RuntimeException(
                    "Unexpected error"
            );
        }
    }

    /**
     * Verifies BusinessException handling.
     *
     * Expected:
     * - HTTP 404
     * - PRODUCT_NOT_FOUND error code
     * - Proper error message
     */
    @Test
    void shouldHandleBusinessException()
            throws Exception {

        mockMvc.perform(get("/business"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code")
                        .value("PRODUCT_NOT_FOUND"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found"));
    }

    /**
     * Verifies AuthorizationDeniedException handling.
     *
     * Expected:
     * - HTTP 403
     * - ACCESS_DENIED error code
     */
    @Test
    void shouldHandleAccessDenied()
            throws Exception {

        mockMvc.perform(get("/access-denied"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code")
                        .value("ACCESS_DENIED"));
    }

    /**
     * Verifies generic exception handling.
     *
     * Expected:
     * - HTTP 500
     * - INTERNAL_SERVER_ERROR error code
     */
    @Test
    void shouldHandleInternalServerError()
            throws Exception {

        mockMvc.perform(get("/internal-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code")
                        .value("INTERNAL_SERVER_ERROR"));
    }
}