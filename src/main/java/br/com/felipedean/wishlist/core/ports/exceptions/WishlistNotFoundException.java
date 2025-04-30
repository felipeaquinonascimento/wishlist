package br.com.felipedean.wishlist.core.ports.exceptions;

public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String message) {
        super(message);
    }
}
