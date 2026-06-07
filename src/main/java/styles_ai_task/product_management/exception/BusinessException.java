package styles_ai_task.product_management.exception;

/**
 * Custom exception used to represent business rule violations
 * and application-specific errors.
 *
 * This exception allows returning a standardized error response
 * containing:
 * - Error Code (machine-readable identifier)
 * - Error Message (human-readable description)
 * - HTTP Status Code
 *
 * Examples:
 * - PRODUCT_NOT_FOUND
 * - DUPLICATE_PRODUCT
 * - INVALID_CREDENTIALS
 * - UNAUTHORIZED_ACCESS
 *
 * This exception is typically handled by a global exception
 * handler to generate consistent API error responses.
 */
public class BusinessException extends RuntimeException {

    /**
     * Unique application-specific error code.
     *
     * Example:
     * PRODUCT_NOT_FOUND
     * USER_ALREADY_EXISTS
     */
    private final String errorCode;

    /**
     * HTTP status code associated with the error.
     *
     * Examples:
     * 400 -> Bad Request
     * 401 -> Unauthorized
     * 404 -> Not Found
     * 409 -> Conflict
     */
    private final int status;

    /**
     * Creates a new business exception.
     *
     * @param errorCode Unique application error code
     * @param message Human-readable error message
     * @param status HTTP status code to be returned
     */
    public BusinessException(
            String errorCode,
            String message,
            int status) {

        super(message);

        this.errorCode = errorCode;
        this.status = status;
    }

    /**
     * Returns the application-specific error code.
     *
     * @return error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns the HTTP status code associated
     * with this exception.
     *
     * @return HTTP status code
     */
    public int getStatus() {
        return status;
    }
}