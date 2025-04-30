package br.com.felipedean.wishlist.core.usecases;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.WishlistRepository;
import br.com.felipedean.wishlist.core.ports.exceptions.WishlistFullException;
import br.com.felipedean.wishlist.core.ports.exceptions.WishlistNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository repository;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductToWishlist_ShouldAddProductToExistingWishlist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";
        Wishlist wishlist = new Wishlist(clientId);
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));
        when(repository.save(wishlist)).thenReturn(wishlist);

        Wishlist result = wishlistService.addProductToWishlist(clientId, productId);

        assertEquals(wishlist, result);
        assertTrue(result.getProductIds().contains(productId));
        verify(repository, times(1)).findByClientId(clientId);
        verify(repository, times(1)).save(wishlist);
    }

    @Test
    void addProductToWishlist_ShouldCreateNewWishlistWhenNotExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-021-UU7V8W9X";
        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());
        when(repository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Wishlist result = wishlistService.addProductToWishlist(clientId, productId);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertTrue(result.getProductIds().contains(productId));
        verify(repository, times(1)).findByClientId(clientId);
        verify(repository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void addProductToWishlist_ShouldThrowExceptionWhenWishlistIsFull() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-003-CC7H8I9J";
        Wishlist wishlist = new Wishlist(clientId);
        for (int i = 0; i < 20; i++) {
            wishlist.addProduct("product" + i);
        }
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertThrows(WishlistFullException.class, () -> wishlistService.addProductToWishlist(clientId, productId));
        verify(repository, times(1)).findByClientId(clientId);
        verify(repository, never()).save(any(Wishlist.class));
    }

    @Test
    void removeProductFromWishlist_ShouldRemoveProduct() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-006-FF6Q7R8S";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        wishlistService.removeProductFromWishlist(clientId, productId);

        assertFalse(wishlist.getProductIds().contains(productId));
        verify(repository, times(1)).findByClientId(clientId);
        verify(repository, times(1)).save(wishlist);
    }

    @Test
    void removeProductFromWishlist_ShouldThrowExceptionWhenWishlistNotFound() {
        String clientId = "0b1c2d3e-4f5a-6b7c-8d9e-0f1a2b3c4d5e";
        String productId = "PRD-007-GG9T0U1V";
        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());

        assertThrows(WishlistNotFoundException.class, () -> wishlistService.removeProductFromWishlist(clientId, productId));
        verify(repository, times(1)).findByClientId(clientId);
        verify(repository, never()).save(any(Wishlist.class));
    }

    @Test
    void getWishlistProducts_ShouldReturnProductList() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct("PRD-014-NN0O1P2Q");
        wishlist.addProduct("PRD-015-OO3R4S5T");
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        List<String> result = wishlistService.getWishlistProducts(clientId);

        assertEquals(wishlist.getProductIds(), result);
        verify(repository, times(1)).findByClientId(clientId);
    }

    @Test
    void getWishlistProducts_ShouldReturnEmptyListWhenWishlistNotFound() {
        String clientId = "4b5c6d7e-8f9a-0b1c-2d3e-4f5a6b7c8d9e";
        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());

        List<String> result = wishlistService.getWishlistProducts(clientId);

        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByClientId(clientId);
    }

    @Test
    void isProductInWishlist_ShouldReturnTrueWhenProductExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-009-II5Z6A7B";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertTrue(result);
        verify(repository, times(1)).findByClientId(clientId);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalseWhenProductDoesNotExist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-022-II5Z6A7B";
        Wishlist wishlist = new Wishlist(clientId);
        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertFalse(result);
        verify(repository, times(1)).findByClientId(clientId);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalseWhenWishlistNotFound() {
        String clientId = "2f3a4b5c-6d7e-8f9a-0b1c-2d3e4f5a6b7c";
        String productId = "PRD-024-SS5D6E7F";
        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertFalse(result);
        verify(repository, times(1)).findByClientId(clientId);
    }
}
