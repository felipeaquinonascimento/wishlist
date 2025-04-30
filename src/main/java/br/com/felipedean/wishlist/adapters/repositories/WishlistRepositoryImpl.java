package br.com.felipedean.wishlist.adapters.repositories;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.core.ports.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<Wishlist> findByClientId(String clientId) {
        Query query = new Query(Criteria.where("clientId").is(clientId));
        Wishlist wishlist = mongoTemplate.findOne(query, Wishlist.class);
        log.info("Buscando wishlist para clientId: {}", clientId);
        log.info("Wishlist encontrada: {}", wishlist);

        List<Wishlist> allWishlists = mongoTemplate.findAll(Wishlist.class);
        log.info("Todas as wishlists no banco: {}", allWishlists);

        return Optional.ofNullable(wishlist);
    }

    @Override
    public Wishlist save(Wishlist wishlist) {
        log.info("Salvando wishlist: {}", wishlist);
        Wishlist savedWishlist = mongoTemplate.save(wishlist);
        log.info("Wishlist salva: {}", savedWishlist);
        return savedWishlist;
    }

    @Override
    public Optional<Wishlist> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Wishlist.class));
    }

    @Override
    public List<Wishlist> findAll() {
        return mongoTemplate.findAll(Wishlist.class);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Wishlist.class);
    }
}