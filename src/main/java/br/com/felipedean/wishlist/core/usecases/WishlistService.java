package br.com.felipedean.wishlist.core.usecases;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.WishlistRepository;
import br.com.felipedean.wishlist.core.ports.exceptions.WishlistFullException;
import br.com.felipedean.wishlist.core.ports.exceptions.WishlistNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;

    public Wishlist addProductToWishlist(String clientId, String productId) {
        log.info("Adicionando produto {} para o cliente {}", productId, clientId);
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElse(new Wishlist(clientId));

        if (wishlist.getProductIds().size() < 20) {
            wishlist.addProduct(productId);
            Wishlist savedWishlist = repository.save(wishlist);
            log.info("Produto {} adicionado com sucesso para o cliente {}", productId, clientId);
            return savedWishlist;
        } else {
            log.warn("Limite de produtos excedido para o cliente {}", clientId);
            throw new WishlistFullException("Wishlist está cheia. Limite máximo de 20 produtos.");
        }
    }

    public void removeProductFromWishlist(String clientId, String productId) {
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist não encontrada"));

        wishlist.removeProduct(productId);
        repository.save(wishlist);
    }

    public List<String> getWishlistProducts(String clientId) {
        log.info("Buscando produtos da wishlist para cliente: {}", clientId);
        Optional<Wishlist> wishlistOptional = repository.findByClientId(clientId);

        if (wishlistOptional.isPresent()) {
            Wishlist wishlist = wishlistOptional.get();
            log.info("Produtos encontrados: {}", wishlist.getProductIds());
            return wishlist.getProductIds();
        } else {
            log.warn("Nenhuma wishlist encontrada para o cliente: {}", clientId);
            return List.of();
        }
    }

    public boolean isProductInWishlist(String clientId, String productId) {
        return repository.findByClientId(clientId)
                .map(wishlist -> wishlist.getProductIds().contains(productId))
                .orElse(false);
    }
}