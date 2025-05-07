package br.com.felipedean.wishlist.dto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WishlistDTOTest {

    @Test
    void testConstructorAndGetters() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        List<String> productIds = Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-BB4E5F6G");

        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setClientId(clientId);
        wishlistDTO.setProductIds(productIds);

        assertEquals(clientId, wishlistDTO.getClientId());
        assertEquals(productIds, wishlistDTO.getProductIds());
    }

    @Test
    void testEmptyConstructor() {
        WishlistDTO wishlistDTO = new WishlistDTO();

        assertNull(wishlistDTO.getClientId());
        assertNull(wishlistDTO.getProductIds());
    }

    @Test
    void testSettersAndGetters() {
        WishlistDTO wishlistDTO = new WishlistDTO();

        wishlistDTO.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlistDTO.setProductIds(Arrays.asList("PRD-003-CC7H8I9J", "PRD-004-DD0K1L2M"));

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", wishlistDTO.getClientId());
        assertEquals(Arrays.asList("PRD-003-CC7H8I9J", "PRD-004-DD0K1L2M"), wishlistDTO.getProductIds());
    }

    @Test
    void testEqualsAndHashCode() {
        WishlistDTO wishlistDTO1 = new WishlistDTO();
        wishlistDTO1.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlistDTO1.setProductIds(Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-BB4E5F6G"));

        WishlistDTO wishlistDTO2 = new WishlistDTO();
        wishlistDTO2.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlistDTO2.setProductIds(Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-BB4E5F6G"));

        WishlistDTO wishlistDTO3 = new WishlistDTO();
        wishlistDTO3.setClientId("9a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c6e");
        wishlistDTO3.setProductIds(Arrays.asList("PRD-005-EE3J4K5L"));

        assertEquals(wishlistDTO1, wishlistDTO2);
        assertNotEquals(wishlistDTO1, wishlistDTO3);
        assertEquals(wishlistDTO1.hashCode(), wishlistDTO2.hashCode());
        assertNotEquals(wishlistDTO1.hashCode(), wishlistDTO3.hashCode());
    }

    @Test
    void testToString() {
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlistDTO.setProductIds(Arrays.asList("PRD-001-AA1B2C3D", "PRD-002-BB4E5F6G"));

        String toStringResult = wishlistDTO.toString();

        assertTrue(toStringResult.contains("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d"));
        assertTrue(toStringResult.contains("PRD-001-AA1B2C3D"));
        assertTrue(toStringResult.contains("PRD-002-BB4E5F6G"));
    }

    @Test
    void testNullValues() {
        WishlistDTO wishlistDTO = new WishlistDTO();

        assertNull(wishlistDTO.getClientId());
        assertNull(wishlistDTO.getProductIds());
    }

    @Test
    void testUpdateValues() {
        WishlistDTO wishlistDTO = new WishlistDTO();

        wishlistDTO.setClientId("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d");
        wishlistDTO.setProductIds(List.of("PRD-005-EE3J4K5L"));

        assertEquals("8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d", wishlistDTO.getClientId());
        assertEquals(List.of("PRD-005-EE3J4K5L"), wishlistDTO.getProductIds());
    }

    @Test
    void testAllArgsConstructor() {
        String clientId = "8a3e5b2c-1d4f-6a7b-9c8d-0e1f2a3b4c5d";
        List<String> productIds = List.of("PRD-001-AA1B2C3D");

        WishlistDTO wishlistDTO = new WishlistDTO(clientId, productIds);

        assertEquals(clientId, wishlistDTO.getClientId());
        assertEquals(productIds, wishlistDTO.getProductIds());
    }
}