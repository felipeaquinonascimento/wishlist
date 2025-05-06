package br.com.felipedean.wishlist.dto;

import lombok.Data;
import java.util.List;

@Data
public class WishlistDTO {
    private String clientId;
    private List<String> productIds;
}
