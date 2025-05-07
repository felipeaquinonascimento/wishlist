package br.com.felipedean.wishlist.core.ports;

import br.com.felipedean.wishlist.adapters.repositories.WishlistRepositoryImpl;
import br.com.felipedean.wishlist.core.domain.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WishlistRepositoryTest {

    @Mock
    private MongoTemplate mongoTemplate;

    private WishlistRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository = new WishlistRepositoryImpl(mongoTemplate);
    }

    @Test
    void findByClientId_ShouldReturnOptionalWishlist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        Wishlist expectedWishlist = new Wishlist(clientId);
        expectedWishlist.addProduct("PRD-001-AA1B2C3D");

        when(mongoTemplate.findOne(any(Query.class), eq(Wishlist.class)))
                .thenReturn(expectedWishlist);

        Optional<Wishlist> result = repository.findByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(expectedWishlist, result.get());

        verify(mongoTemplate).findOne(
                argThat(query ->
                        query.getQueryObject().containsValue(clientId)
                ),
                eq(Wishlist.class)
        );
    }

    @Test
    void findByClientId_ShouldReturnEmptyOptional() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";

        when(mongoTemplate.findOne(any(Query.class), eq(Wishlist.class)))
                .thenReturn(null);

        Optional<Wishlist> result = repository.findByClientId(clientId);

        assertTrue(result.isEmpty());

        verify(mongoTemplate).findOne(
                argThat(query ->
                        query.getQueryObject().containsValue(clientId)
                ),
                eq(Wishlist.class)
        );
    }

    @Test
    void findById_ShouldReturnOptionalWishlist() {
        String id = "681250f1f50f904217d077b1";
        Wishlist expectedWishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        expectedWishlist.addProduct("PRD-001-AA1B2C3D");

        when(mongoTemplate.findById(id, Wishlist.class))
                .thenReturn(expectedWishlist);

        Optional<Wishlist> result = repository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedWishlist, result.get());

        verify(mongoTemplate).findById(id, Wishlist.class);
    }

    @Test
    void findAll_ShouldReturnListOfWishlists() {
        Wishlist wishlist1 = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlist1.addProduct("PRD-001-AA1B2C3D");

        Wishlist wishlist2 = new Wishlist("7a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c4f");
        wishlist2.addProduct("PRD-002-AA1B2C3D");

        List<Wishlist> expectedWishlists = List.of(wishlist1, wishlist2);

        when(mongoTemplate.findAll(Wishlist.class))
                .thenReturn(expectedWishlists);

        List<Wishlist> result = repository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedWishlists, result);

        verify(mongoTemplate).findAll(Wishlist.class);
    }

    @Test
    void save_ShouldSaveWishlist() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlist.addProduct("PRD-001-AA1B2C3D");

        when(mongoTemplate.save(wishlist))
                .thenReturn(wishlist);

        Wishlist savedWishlist = repository.save(wishlist);

        assertNotNull(savedWishlist);
        assertEquals(wishlist, savedWishlist);

        verify(mongoTemplate).save(wishlist);
    }

    @Test
    void deleteById_ShouldDeleteWishlist() {
        String id = "681250f1f50f904217d077b1";

        repository.deleteById(id);

        verify(mongoTemplate).remove(
                argThat(query ->
                        query.getQueryObject().containsValue(id)
                ),
                eq(Wishlist.class)
        );
    }
}