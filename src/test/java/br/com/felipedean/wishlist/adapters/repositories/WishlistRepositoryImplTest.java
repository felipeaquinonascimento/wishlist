package br.com.felipedean.wishlist.adapters.repositories;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistRepositoryImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private WishlistRepositoryImpl wishlistRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByClientId_ShouldReturnOptionalWishlist() {
        String clientId = "clientId";
        Wishlist wishlist = new Wishlist(clientId);
        Query query = new Query(Criteria.where("clientId").is(clientId));
        when(mongoTemplate.findOne(query, Wishlist.class)).thenReturn(wishlist);
        when(mongoTemplate.findAll(Wishlist.class)).thenReturn(Arrays.asList(wishlist));

        Optional<Wishlist> result = wishlistRepository.findByClientId(clientId);

        assertTrue(result.isPresent());
        assertEquals(wishlist, result.get());
        verify(mongoTemplate, times(1)).findOne(query, Wishlist.class);
        verify(mongoTemplate, times(1)).findAll(Wishlist.class);
    }

    @Test
    void findByClientId_ShouldReturnEmptyOptional() {
        String clientId = "clientId";
        Query query = new Query(Criteria.where("clientId").is(clientId));
        when(mongoTemplate.findOne(query, Wishlist.class)).thenReturn(null);
        when(mongoTemplate.findAll(Wishlist.class)).thenReturn(Arrays.asList());

        Optional<Wishlist> result = wishlistRepository.findByClientId(clientId);

        assertFalse(result.isPresent());
        verify(mongoTemplate, times(1)).findOne(query, Wishlist.class);
        verify(mongoTemplate, times(1)).findAll(Wishlist.class);
    }

    @Test
    void save_ShouldReturnSavedWishlist() {
        Wishlist wishlist = new Wishlist("clientId");
        when(mongoTemplate.save(wishlist)).thenReturn(wishlist);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        assertEquals(wishlist, savedWishlist);
        verify(mongoTemplate, times(1)).save(wishlist);
    }

    @Test
    void findById_ShouldReturnOptionalWishlist() {
        String id = "wishlistId";
        Wishlist wishlist = new Wishlist("clientId");
        when(mongoTemplate.findById(id, Wishlist.class)).thenReturn(wishlist);

        Optional<Wishlist> result = wishlistRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(wishlist, result.get());
        verify(mongoTemplate, times(1)).findById(id, Wishlist.class);
    }

    @Test
    void findById_ShouldReturnEmptyOptional() {
        String id = "wishlistId";
        when(mongoTemplate.findById(id, Wishlist.class)).thenReturn(null);

        Optional<Wishlist> result = wishlistRepository.findById(id);

        assertFalse(result.isPresent());
        verify(mongoTemplate, times(1)).findById(id, Wishlist.class);
    }

    @Test
    void findAll_ShouldReturnListOfWishlists() {
        List<Wishlist> wishlists = Arrays.asList(new Wishlist("clientId1"), new Wishlist("clientId2"));
        when(mongoTemplate.findAll(Wishlist.class)).thenReturn(wishlists);

        List<Wishlist> result = wishlistRepository.findAll();

        assertEquals(wishlists, result);
        verify(mongoTemplate, times(1)).findAll(Wishlist.class);
    }

    @Test
    void deleteById_ShouldRemoveWishlistById() {
        String id = "wishlistId";
        Query query = new Query(Criteria.where("_id").is(id));

        wishlistRepository.deleteById(id);

        verify(mongoTemplate, times(1)).remove(query, Wishlist.class);
    }
}
