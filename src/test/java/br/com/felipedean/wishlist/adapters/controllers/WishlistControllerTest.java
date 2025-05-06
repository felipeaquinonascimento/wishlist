package br.com.felipedean.wishlist.adapters.controllers;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.exceptions.GlobalExceptionHandler;
import br.com.felipedean.wishlist.core.usecases.WishlistService;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProduct_ShouldReturnCreatedWishlist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";

        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);

        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setClientId(clientId);
        wishlistDTO.setProductIds(List.of(productId));

        when(wishlistService.addProductToWishlist(clientId, productId)).thenReturn(wishlistDTO);

        ResponseEntity<WishlistDTO> response = wishlistController.addProduct(clientId, productId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(wishlistDTO, response.getBody());
        verify(wishlistService, times(1)).addProductToWishlist(clientId, productId);
    }

    @Test
    void addProduct_ShouldThrowExceptionWhenWishlistIsFull() {
        // Arrange
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-021-UU7V8W9X";

        when(wishlistService.addProductToWishlist(clientId, productId))
                .thenThrow(new GlobalExceptionHandler.WishlistFullException("Wishlist está cheia"));

        // Act & Assert
        assertThrows(GlobalExceptionHandler.WishlistFullException.class, () -> {
            wishlistController.addProduct(clientId, productId);
        });
    }

    @Test
    void removeProduct_ShouldReturnNoContent() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-019-SS5D6E7F";

        ResponseEntity<Void> response = wishlistController.removeProduct(clientId, productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(wishlistService, times(1)).removeProductFromWishlist(clientId, productId);
    }

    @Test
    void removeProduct_ShouldThrowWishlistNotFoundExceptionWhenWishlistDoesNotExist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-007-GG9T0U1V";
        doThrow(new GlobalExceptionHandler.WishlistNotFoundException("Wishlist não encontrada"))
                .when(wishlistService).removeProductFromWishlist(clientId, productId);

        assertThrows(GlobalExceptionHandler.WishlistNotFoundException.class,
                () -> wishlistController.removeProduct(clientId, productId));
        verify(wishlistService, times(1)).removeProductFromWishlist(clientId, productId);
    }

    @Test
    void getWishlistProducts_ShouldReturnProductList() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        List<String> products = List.of("PRD-003-CC7H8I9J", "PRD-004-DD0K1L2M");
        when(wishlistService.getWishlistProducts(clientId)).thenReturn(products);

        ResponseEntity<List<String>> response = wishlistController.getWishlistProducts(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(wishlistService, times(1)).getWishlistProducts(clientId);
    }

    @Test
    void checkProductInWishlist_ShouldReturnOkWhenProductExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-002-BB4E5F6G";
        when(wishlistService.isProductInWishlist(clientId, productId)).thenReturn(true);

        ResponseEntity<Void> response = wishlistController.checkProductInWishlist(clientId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(wishlistService, times(1)).isProductInWishlist(clientId, productId);
    }

    @Test
    void checkProductInWishlist_ShouldReturnNotFoundWhenProductDoesNotExist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-021-UU7V8W9X";
        when(wishlistService.isProductInWishlist(clientId, productId)).thenReturn(false);

        ResponseEntity<Void> response = wishlistController.checkProductInWishlist(clientId, productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(wishlistService, times(1)).isProductInWishlist(clientId, productId);
    }
}