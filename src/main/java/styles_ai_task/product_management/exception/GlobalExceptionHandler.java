package styles_ai_task.product_management.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 *
 * This class centralizes exception handling and ensures
 * that all API errors are returned in a consistent format.
 *
 * Benefits:
 * - Keeps controller code clean
 * - Provides standardized error responses
 * - Improves API maintainability
 * - Simplifies debugging and error tracking
 *
 * Common handled exceptions:
 * - Validation errors
 * - Invalid request payloads
 * - Business rule violations
 * - Database constraint violations
 * - Access denied errors
 * - Unsupported HTTP methods
 * - Unhandled server exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles bean validation failures.
     *
     * Triggered when a request body annotated with
     * @Valid fails validation constraints such as:
     * - @NotBlank
     * - @NotNull
     * - @Positive
     * - @Min
     *
     * Example:
     * {
     *   "name": ""
     * }
     *
     * Response:
     * {
     *   "code": "INVALID_REQUEST",
     *   "message": "Validation failed",
     *   "errors": {
     *      "name": "Product name is required"
     *   }
     * }
     *
     * @param ex validation exception
     * @return 400 Bad Request response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(
                        error.getField(),
                        error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCodes.INVALID_REQUEST)
                                .message("Validation failed")
                                .errors(errors)
                                .build());
    }

    /**
     * Handles malformed JSON payloads and datatype mismatches.
     *
     * Examples:
     * {
     *   "price": "abc"
     * }
     *
     * When Spring cannot deserialize the request body,
     * this exception is triggered.
     *
     * Response:
     * {
     *   "code": "INVALID_REQUEST",
     *   "message": "Invalid value provided for field 'price'"
     * }
     *
     * @param ex JSON parsing exception
     * @return 400 Bad Request response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        String message = "Invalid request payload";

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException invalidFormatException) {

            String fieldName = invalidFormatException.getPath()
                    .stream()
                    .findFirst()
                    .map(ref -> ref.getFieldName())
                    .orElse("field");

            message = String.format(
                    "Invalid value provided for field '%s'",
                    fieldName);
        }

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCodes.INVALID_REQUEST)
                                .message(message)
                                .build());
    }

    /**
     * Handles custom business exceptions.
     *
     * Used when business rules are violated.
     *
     * Examples:
     * - Product not found
     * - Product already exists
     * - Invalid business operation
     *
     * @param ex custom business exception
     * @return response with custom HTTP status code
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex) {

        return ResponseEntity.status(ex.getStatus())
                .body(
                        ErrorResponse.builder()
                                .code(ex.getErrorCode())
                                .message(ex.getMessage())
                                .build());
    }

    /**
     * Handles database constraint violations.
     *
     * Common causes:
     * - Duplicate unique values
     * - Foreign key violations
     * - Null values in required columns
     *
     * Example:
     * Creating a product with an already existing name.
     *
     * @param ex database exception
     * @return 409 Conflict response
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        return ResponseEntity.status(409)
                .body(
                        ErrorResponse.builder()
                                .code("DATA_INTEGRITY_VIOLATION")
                                .message("Request violates database constraints")
                                .build());
    }

    /**
     * Handles unsupported HTTP methods.
     *
     * Example:
     * Calling PUT on an endpoint that only supports POST.
     *
     * @param ex unsupported method exception
     * @return 405 Method Not Allowed response
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        return ResponseEntity.status(405)
                .body(
                        ErrorResponse.builder()
                                .code("METHOD_NOT_ALLOWED")
                                .message("HTTP method not supported")
                                .build());
    }

    /**
     * Handles authorization failures.
     *
     * Triggered when an authenticated user
     * does not have sufficient permissions
     * to access a resource.
     *
     * Example:
     * VIEW user attempting to create a product.
     *
     * Response:
     * {
     *   "code": "ACCESS_DENIED",
     *   "message": "You do not have permission to perform this action"
     * }
     *
     * @param ex authorization exception
     * @return 403 Forbidden response
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AuthorizationDeniedException ex) {

        return ResponseEntity.status(403)
                .body(
                        ErrorResponse.builder()
                                .code("ACCESS_DENIED")
                                .message("You do not have permission to perform this action")
                                .build());
    }

    /**
     * Fallback exception handler.
     *
     * Catches any unexpected exceptions that
     * are not handled by more specific handlers.
     *
     * Prevents internal implementation details
     * from being exposed to API consumers.
     *
     * @param ex unexpected exception
     * @return 500 Internal Server Error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex) {

        return ResponseEntity.internalServerError()
                .body(
                        ErrorResponse.builder()
                                .code("INTERNAL_SERVER_ERROR")
                                .message("Something went wrong")
                                .build());
    }
}