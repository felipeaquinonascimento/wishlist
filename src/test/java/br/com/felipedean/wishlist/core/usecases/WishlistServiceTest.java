package br.com.felipedean.wishlist.core.usecases;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.WishlistRepository;
import br.com.felipedean.wishlist.core.ports.exceptions.GlobalExceptionHandler;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import br.com.felipedean.wishlist.mapper.WishlistMapper;
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

    @Mock
    private WishlistMapper mapper;

    @InjectMocks
    private WishlistService wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductToWishlist_ShouldAddProductSuccessfully() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";
        Wishlist wishlist = new Wishlist(clientId);
        Wishlist savedWishlist = new Wishlist(clientId);
        savedWishlist.addProduct(productId);

        WishlistDTO expectedDTO = new WishlistDTO();
        expectedDTO.setClientId(clientId);
        expectedDTO.setProductIds(List.of(productId));

        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));
        when(repository.save(wishlist)).thenReturn(savedWishlist);
        when(mapper.toDTO(savedWishlist)).thenReturn(expectedDTO);

        WishlistDTO result = wishlistService.addProductToWishlist(clientId, productId);

        assertNotNull(result);
        assertEquals(clientId, result.getClientId());
        assertTrue(result.getProductIds().contains(productId));
        verify(repository).save(wishlist);
    }

    @Test
    void addProductToWishlist_ShouldThrowExceptionWhenWishlistIsFull() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        Wishlist wishlist = new Wishlist(clientId);

        for (int i = 1; i <= 20; i++) {
            wishlist.addProduct("PRD-001-AA1B2C3D" + i);
        }

        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        assertThrows(GlobalExceptionHandler.WishlistFullException.class, () -> {
            wishlistService.addProductToWishlist(clientId, "PRD-021-AA1B2C3D");
        });
    }

    @Test
    void getWishlistProducts_ShouldReturnProductsWhenWishlistExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct("PRD-001-AA1B2C3D");
        wishlist.addProduct("PRD-002-AA1B2C3D");

        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        List<String> products = wishlistService.getWishlistProducts(clientId);

        assertNotNull(products);
        assertEquals(2, products.size());
        assertTrue(products.contains("PRD-001-AA1B2C3D"));
        assertTrue(products.contains("PRD-002-AA1B2C3D"));
    }

    @Test
    void getWishlistProducts_ShouldReturnEmptyListWhenWishlistNotFound() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());

        List<String> products = wishlistService.getWishlistProducts(clientId);

        assertTrue(products.isEmpty());
    }

    @Test
    void isProductInWishlist_ShouldReturnTrueWhenProductExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";
        Wishlist wishlist = new Wishlist(clientId);
        wishlist.addProduct(productId);

        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertTrue(result);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalseWhenProductNotExists() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";
        Wishlist wishlist = new Wishlist(clientId);

        when(repository.findByClientId(clientId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertFalse(result);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalseWhenWishlistNotFound() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        String productId = "PRD-001-AA1B2C3D";

        when(repository.findByClientId(clientId)).thenReturn(Optional.empty());

        boolean result = wishlistService.isProductInWishlist(clientId, productId);

        assertFalse(result);
    }
}