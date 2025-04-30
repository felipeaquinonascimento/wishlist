package br.com.felipedean.wishlist.core.ports.exceptions;

public class WishlistFullException extends RuntimeException {
    public WishlistFullException(String message) {
        super(message);
    }
}
