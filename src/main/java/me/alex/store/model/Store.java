package me.alex.store.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private Long id;
    @Getter(AccessLevel.NONE)
    private List<Long> products;

    public void addProduct(Long product) {
        products.add(product);
    }

    public void deleteProduct(Long productId) {
        Optional<Long> product = findProduct(productId);
        product.ifPresent(p -> products.remove(p));
    }

    public List<Long> findAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Optional<Long> findProduct(Long productId) {
        return products.stream()
                .filter(p -> p.equals(productId))
                .findFirst();

    }

}
