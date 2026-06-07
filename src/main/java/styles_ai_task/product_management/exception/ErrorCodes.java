package styles_ai_task.product_management.exception;

/**
 * Centralized application error codes.
 *
 * This utility class contains all predefined error codes
 * used throughout the application for business and validation
 * exceptions.
 *
 * Using constants helps:
 * - Maintain consistency across APIs
 * - Avoid hardcoded strings
 * - Simplify error handling on the client side
 * - Improve maintainability
 *
 * Example API Error Response:
 *
 * {
 * "errorCode": "PRODUCT_NOT_FOUND",
 * "message": "Product not found with id: xxx"
 * }
 */
public final class ErrorCodes {

    /**
     * Private constructor to prevent instantiation.
     *
     * This class is intended to be used only as a container
     * for static constants.
     */
    private ErrorCodes() {
    }

    /**
     * Returned when a requested product does not exist.
     *
     * Example:
     * - GET /products/{id}
     * - PUT /products/{id}
     * - DELETE /products/{id}
     */
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";

    /**
     * Returned when attempting to create a product
     * with a name that already exists.
     *
     * Example:
     * POST /products
     */
    public static final String PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";

    /**
     * Returned when the request payload fails validation
     * or contains invalid data.
     *
     * Examples:
     * - Missing required fields
     * - Negative quantity
     * - Invalid price value
     * - Malformed request body
     */
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
}