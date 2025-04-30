package br.com.felipedean.wishlist.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WishlistTest {

    @Test
    void addProduct_ShouldAddProductToWishlist() {
        String clientId = "clientId";
        String productId = "productId";
        Wishlist wishlist = new Wishlist(clientId);

        boolean result = wishlist.addProduct(productId);

        assertTrue(result);
        assertTrue(wishlist.getProductIds().contains(productId));
    }

    @Test
    void addProduct_ShouldNotAddDuplicateProduct() {
        String clientId = "clientId";
        String productId = "productId";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);

        boolean result = wishlist.addProduct(productId);

        assertFalse(result);
        assertEquals(1, wishlist.getProductIds().size());
    }

    @Test
    void addProduct_ShouldNotAddProductWhenWishlistIsFull() {
        String clientId = "clientId";
        Wishlist wishlist = new Wishlist(clientId);
        for (int i = 0; i < 20; i++) {
            wishlist.addProduct("product" + i);
        }

        boolean result = wishlist.addProduct("productId");

        assertFalse(result);
        assertEquals(20, wishlist.getProductIds().size());
    }

    @Test
    void removeProduct_ShouldRemoveProductFromWishlist() {
        String clientId = "clientId";
        String productId = "productId";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);

        wishlist.removeProduct(productId);

        assertFalse(wishlist.getProductIds().contains(productId));
    }

    @Test
    void removeProduct_ShouldNotRemoveNonExistentProduct() {
        String clientId = "clientId";
        String productId = "productId";
        Wishlist wishlist = new Wishlist(clientId);

        wishlist.removeProduct(productId);

        assertTrue(wishlist.getProductIds().isEmpty());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        String clientId = "clientId";
        String productId = "productId";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);

        String result = wishlist.toString();

        assertTrue(result.contains("id="));
        assertTrue(result.contains("clientId='clientId'"));
        assertTrue(result.contains("productIds=[productId]"));
    }
}
