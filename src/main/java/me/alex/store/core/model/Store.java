package me.alex.store.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    private Long id;
    @Embedded(onEmpty = USE_EMPTY)
    private StoreDescription storeDescription;
    @Getter(AccessLevel.NONE)
    private List<Long> productRefs;

    public void addProduct(Long product) {
        productRefs.add(product);
    }

    public void deleteProduct(Long productId) {
        Optional<Long> product = findProduct(productId);
        product.ifPresent(p -> productRefs.remove(p));
    }

    public List<Long> findAllProducts() {
        return Collections.unmodifiableList(productRefs);
    }

    public Optional<Long> findProduct(Long productId) {
        return productRefs.stream()
                .filter(p -> p.equals(productId))
                .findFirst();
    }

}
