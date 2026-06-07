package styles_ai_task.product_management.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Standard API error response model.
 *
 * This DTO is returned whenever an exception occurs
 * in the application and is used to provide a
 * consistent error structure to API consumers.
 *
 * It supports:
 * - Error code for programmatic handling
 * - Human-readable error message
 * - Field-level validation errors
 *
 * Example - Business Exception:
 * {
 *   "code": "PRODUCT_NOT_FOUND",
 *   "message": "Product not found",
 *   "errors": null
 * }
 *
 * Example - Validation Error:
 * {
 *   "code": "INVALID_REQUEST",
 *   "message": "Validation failed",
 *   "errors": {
 *     "name": "Product name is required",
 *     "price": "Price must be greater than 0"
 *   }
 * }
 */
@Data
@Builder
public class ErrorResponse {

    /**
     * Application-specific error code.
     *
     * Examples:
     * - PRODUCT_NOT_FOUND
     * - PRODUCT_ALREADY_EXISTS
     * - INVALID_REQUEST
     */
    private String code;

    /**
     * Human-readable error message.
     *
     * Intended to help API consumers understand
     * the reason for the failure.
     */
    private String message;

    /**
     * Validation errors mapped by field name.
     *
     * Used mainly for request validation failures.
     *
     * Example:
     * {
     *   "name": "Product name is required",
     *   "price": "Price must be greater than 0"
     * }
     */
    private Map<String, String> errors;
}