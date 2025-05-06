package br.com.felipedean.wishlist.core.domain;

import br.com.felipedean.wishlist.core.ports.exceptions.GlobalExceptionHandler;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Document(collection = "wishlists")
public class Wishlist {
    private static final int MAX_WISHLIST_SIZE = 20;

    @Id
    private String id;

    @Indexed(unique = true)
    private final String clientId;

    private List<String> productIds;

    public Wishlist() {
        this.clientId = null;
        this.productIds = new ArrayList<>();
    }

    public Wishlist(String clientId) {
        this.clientId = Objects.requireNonNull(clientId, "Client ID cannot be null");
        this.productIds = new ArrayList<>();
    }

    public boolean addProduct(String productId) {
        Objects.requireNonNull(productId, "Product ID não pode ser nulo");

        if (productId.isBlank()) {
            throw new IllegalArgumentException("Product ID inválido");
        }

        if (productIds.size() >= MAX_WISHLIST_SIZE) {
            throw new GlobalExceptionHandler.WishlistFullException("Limite máximo de produtos atingido");
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

    public void setProductIds(List<String> productIds) {
        if (productIds == null) {
            this.productIds = new ArrayList<>();
            return;
        }

        if (productIds.size() > MAX_WISHLIST_SIZE) {
            throw new GlobalExceptionHandler.WishlistFullException("Limite máximo de produtos excedido");
        }

        this.productIds = new ArrayList<>(productIds);
    }
}