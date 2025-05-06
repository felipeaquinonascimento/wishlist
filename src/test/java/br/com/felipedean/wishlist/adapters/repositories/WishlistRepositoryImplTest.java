package br.com.felipedean.wishlist.adapters.repositories;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WishlistRepositoryImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private WishlistRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByClientId_ShouldReturnOptionalWishlist() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        Wishlist expectedWishlist = new Wishlist(clientId);

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
}