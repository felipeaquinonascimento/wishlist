package br.com.felipedean.wishlist.core.usecases;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.WishlistRepository;
import br.com.felipedean.wishlist.core.ports.exceptions.GlobalExceptionHandler;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import br.com.felipedean.wishlist.mapper.WishlistMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.ServiceUnavailableException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final WishlistMapper mapper;

    @CircuitBreaker(name = "wishlistService", fallbackMethod = "fallbackAddProduct")
    public WishlistDTO addProductToWishlist(String clientId, String productId) {
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElse(new Wishlist(clientId));

        wishlist.addProduct(productId);
        Wishlist savedWishlist = repository.save(wishlist);

        return mapper.toDTO(savedWishlist);
    }

    public WishlistDTO fallbackAddProduct(String clientId, String productId, Exception ex) throws ServiceUnavailableException {
        log.error("Falha ao adicionar produto à wishlist", ex);
        throw new ServiceUnavailableException("Serviço temporariamente indisponível");
    }

    public void removeProductFromWishlist(String clientId, String productId) {
        Wishlist wishlist = repository.findByClientId(clientId)
                .orElseThrow(() -> new GlobalExceptionHandler.WishlistNotFoundException("Wishlist não encontrada"));

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
            return Collections.emptyList();
        }
    }

    public boolean isProductInWishlist(String clientId, String productId) {
        return repository.findByClientId(clientId)
                .map(wishlist -> wishlist.getProductIds().contains(productId))
                .orElse(false);
    }
}