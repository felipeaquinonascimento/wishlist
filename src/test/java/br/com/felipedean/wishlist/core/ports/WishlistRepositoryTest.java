package br.com.felipedean.wishlist.core.ports;

import br.com.felipedean.wishlist.adapters.repositories.WishlistRepositoryImpl;
import br.com.felipedean.wishlist.core.domain.Wishlist;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class WishlistRepositoryTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public WishlistRepository wishlistRepository(MongoTemplate mongoTemplate) {
            return new WishlistRepositoryImpl(mongoTemplate);
        }
    }

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        // Limpa o banco de dados antes de cada teste
        mongoTemplate.getDb().drop();
    }

    @AfterEach
    void tearDown() {
        // Limpa o banco de dados após cada teste
        mongoTemplate.getDb().drop();
    }

    @Test
    void findByClientId_ShouldReturnOptionalWishlist() {
        String clientId = "clientId";
        Wishlist wishlist = new Wishlist(clientId);
        wishlistRepository.save(wishlist);

        Optional<Wishlist> result = wishlistRepository.findByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(wishlist.getClientId(), result.get().getClientId());
    }

    @Test
    void findByClientId_ShouldReturnEmptyOptional() {
        String clientId = "clientId";

        Optional<Wishlist> result = wishlistRepository.findByClientId(clientId);

        assertFalse(result.isPresent());
    }

    @Test
    void save_ShouldSaveWishlist() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        assertNotNull(savedWishlist.getId());
        assertEquals(wishlist.getClientId(), savedWishlist.getClientId());
    }

    @Test
    void findById_ShouldReturnOptionalWishlist() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        Optional<Wishlist> result = wishlistRepository.findById(savedWishlist.getId());

        assertTrue(result.isPresent());
        assertEquals(savedWishlist.getId(), result.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyOptional() {
        String id = "non-existent-id";

        Optional<Wishlist> result = wishlistRepository.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnListOfWishlists() {
        Wishlist wishlist1 = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        Wishlist wishlist2 = new Wishlist("7e8f9a0b-1c2d-3e4f-5a6b-7c8d9e0f1a2b");
        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);

        List<Wishlist> result = wishlistRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteById_ShouldDeleteWishlist() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        wishlistRepository.deleteById(savedWishlist.getId());

        Optional<Wishlist> deletedWishlist = wishlistRepository.findById(savedWishlist.getId());
        assertFalse(deletedWishlist.isPresent());
    }
}
