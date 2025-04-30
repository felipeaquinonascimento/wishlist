package br.com.felipedean.wishlist.core.ports;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository {
    Optional<Wishlist> findByClientId(String clientId);
    Wishlist save(Wishlist wishlist);
    Optional<Wishlist> findById(String id);
    List<Wishlist> findAll();
    void deleteById(String id);
}
