package br.com.felipedean.wishlist.mapper;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WishlistMapperTest {

    private WishlistMapper wishlistMapper;

    @BeforeEach
    void setUp() {
        wishlistMapper = new WishlistMapper();
    }

    @Test
    void toEntity_ShouldMapDTOToEntity() {
        WishlistDTO dto = new WishlistDTO();
        dto.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        dto.setProductIds(Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-AA1B2C3D"));

        Wishlist wishlist = wishlistMapper.toEntity(dto);

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", wishlist.getClientId());
        assertEquals(2, wishlist.getProductIds().size());
        assertTrue(wishlist.getProductIds().contains("PRD-001-AA1B2C3D"));
        assertTrue(wishlist.getProductIds().contains("PRD-002-AA1B2C3D"));
    }

    @Test
    void toEntity_ShouldHandleEmptyProductIds() {
        WishlistDTO dto = new WishlistDTO();
        dto.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        dto.setProductIds(Collections.emptyList());

        Wishlist wishlist = wishlistMapper.toEntity(dto);

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", wishlist.getClientId());
        assertTrue(wishlist.getProductIds().isEmpty());
    }

    @Test
    void toEntity_ShouldHandleNullProductIds() {
        WishlistDTO dto = new WishlistDTO();
        dto.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        dto.setProductIds(null);

        Wishlist wishlist = wishlistMapper.toEntity(dto);

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", wishlist.getClientId());
        assertTrue(wishlist.getProductIds().isEmpty());
    }

    @Test
    void toDTO_ShouldMapEntityToDTO() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlist.addProduct("PRD-001-AA1B2C3D");
        wishlist.addProduct("PRD-002-AA1B2C3D");

        WishlistDTO dto = wishlistMapper.toDTO(wishlist);

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", dto.getClientId());
        assertEquals(2, dto.getProductIds().size());
        assertTrue(dto.getProductIds().contains("PRD-001-AA1B2C3D"));
        assertTrue(dto.getProductIds().contains("PRD-002-AA1B2C3D"));
    }

    @Test
    void toDTO_ShouldHandleEmptyProductIds() {
        Wishlist wishlist = new Wishlist("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");

        WishlistDTO dto = wishlistMapper.toDTO(wishlist);

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", dto.getClientId());
        assertTrue(dto.getProductIds().isEmpty());
    }

    @Test
    void toEntity_WithNullDTO_ShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            wishlistMapper.toEntity(null);
        });
    }

    @Test
    void roundTripMapping_ShouldPreserveData() {
        WishlistDTO originalDTO = new WishlistDTO();
        originalDTO.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        originalDTO.setProductIds(Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-AA1B2C3D"));

        Wishlist intermediateEntity = wishlistMapper.toEntity(originalDTO);
        WishlistDTO resultDTO = wishlistMapper.toDTO(intermediateEntity);

        assertEquals(originalDTO.getClientId(), resultDTO.getClientId());
        assertEquals(originalDTO.getProductIds(), resultDTO.getProductIds());
    }
}