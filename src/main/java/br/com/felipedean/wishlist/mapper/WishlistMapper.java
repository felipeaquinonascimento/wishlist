package br.com.felipedean.wishlist.mapper;

import br.com.felipedean.wishlist.core.domain.Wishlist;
import br.com.felipedean.wishlist.dto.WishlistDTO;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {
    public Wishlist toEntity(WishlistDTO dto) {
        Wishlist wishlist = new Wishlist(dto.getClientId());
        wishlist.setProductIds(dto.getProductIds());
        return wishlist;
    }

    public WishlistDTO toDTO(Wishlist entity) {
        WishlistDTO dto = new WishlistDTO();
        dto.setClientId(entity.getClientId());
        dto.setProductIds(entity.getProductIds());
        return dto;
    }
}
