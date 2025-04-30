package br.com.felipedean.wishlist.core.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "wishlists")
public class Wishlist {
    private static final int MAX_WISHLIST_SIZE = 20;

    @Id
    private String id;

    @Indexed(unique = true)
    private String clientId;

    private List<String> productIds;

    // Construtor padrão necessário para o MongoDB
    public Wishlist() {
        this.productIds = new ArrayList<>();
    }

    public Wishlist(String clientId) {
        this.clientId = clientId;
        this.productIds = new ArrayList<>();
    }

    public boolean addProduct(String productId) {
        if (productIds.size() >= MAX_WISHLIST_SIZE) {
            return false;
        }
        if (!productIds.contains(productId)) {
            productIds.add(productId);
            return true;
        }
        return false;
    }

    public void removeProduct(String productId) {
        productIds.remove(productId);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", productIds=" + productIds +
                '}';
    }
}