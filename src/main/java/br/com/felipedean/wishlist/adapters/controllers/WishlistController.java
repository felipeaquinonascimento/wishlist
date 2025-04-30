package br.com.felipedean.wishlist.adapters.controllers;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.usecases.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{clientId}/products/{productId}")
    public ResponseEntity<Wishlist> addProduct(
            @PathVariable String clientId,
            @PathVariable String productId
    ) {
        Wishlist wishlist = wishlistService.addProductToWishlist(clientId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlist);
    }

    @DeleteMapping("/{clientId}/products/{productId}")
    public ResponseEntity<Void> removeProduct(
            @PathVariable String clientId,
            @PathVariable String productId
    ) {
        wishlistService.removeProductFromWishlist(clientId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{clientId}/products")
    public ResponseEntity<List<String>> getWishlistProducts(
            @PathVariable String clientId
    ) {
        List<String> products = wishlistService.getWishlistProducts(clientId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{clientId}/products/{productId}")
    public ResponseEntity<Boolean> checkProductInWishlist(
            @PathVariable String clientId,
            @PathVariable String productId
    ) {
        boolean isInWishlist = wishlistService.isProductInWishlist(clientId, productId);
        return ResponseEntity.ok(isInWishlist);
    }
}