package br.com.felipedean.wishlist.core.ports.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static class WishlistNotFoundException extends RuntimeException {
        public WishlistNotFoundException(String message) {
            super(message);
        }
    }

    public static class WishlistFullException extends RuntimeException {
        public WishlistFullException(String message) {
            super(message);
        }
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class DatabaseConnectionException extends RuntimeException {
        public DatabaseConnectionException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWishlistNotFound(WishlistNotFoundException ex) {
        log.error("Wishlist not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(WishlistFullException.class)
    public ResponseEntity<ErrorResponse> handleWishlistFull(WishlistFullException ex) {
        log.warn("Wishlist capacity exceeded: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        log.error("Product not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConnection(DatabaseConnectionException ex) {
        log.error("Database connection error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponse("SERVICE_UNAVAILABLE", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Invalid argument: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Erro interno no servidor"));
    }

    public record ErrorResponse(String code, String message) {}
}