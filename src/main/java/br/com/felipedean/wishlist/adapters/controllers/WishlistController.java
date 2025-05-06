package br.com.felipedean.wishlist.adapters.controllers;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.usecases.WishlistService;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import br.com.felipedean.wishlist.mapper.WishlistMapper;
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
    private final WishlistMapper wishlistMapper;

    @PostMapping("/{clientId}/products/{productId}")
    public ResponseEntity<WishlistDTO> addProduct(
            @PathVariable String clientId,
            @PathVariable String productId
    ) {
        WishlistDTO wishlistDTO = wishlistService.addProductToWishlist(clientId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistDTO);
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
    public ResponseEntity<Void> checkProductInWishlist(
            @PathVariable String clientId,
            @PathVariable String productId
    ) {
        boolean exists = wishlistService.isProductInWishlist(clientId, productId);
        return exists
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}