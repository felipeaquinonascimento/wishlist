package br.com.felipedean.wishlist.core.domain;

import br.com.felipedean.wishlist.core.ports.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WishlistTest {

    @Test
    void addProduct_ShouldAddProductSuccessfully() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        boolean result = wishlist.addProduct("PRD-001-AA1B2C3D");

        assertTrue(result);
        assertEquals(1, wishlist.getProductIds().size());
        assertTrue(wishlist.getProductIds().contains("PRD-001-AA1B2C3D"));
    }

    @Test
    void addProduct_ShouldNotAddDuplicateProduct() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlist.addProduct("PRD-001-AA1B2C3D");

        boolean result = wishlist.addProduct("PRD-001-AA1B2C3D");

        assertFalse(result);
        assertEquals(1, wishlist.getProductIds().size());
    }

    @Test
    void addProduct_ShouldNotAddProductWhenWishlistIsFull() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");

        for (int i = 1; i <= 20; i++) {
            wishlist.addProduct("PRD-001-AA1B2C3D" + i);
        }

        assertThrows(GlobalExceptionHandler.WishlistFullException.class, () -> {
            wishlist.addProduct("PRD-001-AA1B2C3D");
        });
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlist.addProduct("PRD-001-AA1B2C3D");

        String expected = "Wishlist{id='null', clientId='8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d', productIds=[PRD-001-AA1B2C3D]}";
        assertEquals(expected, wishlist.toString());
    }
}