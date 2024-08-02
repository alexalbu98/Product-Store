package me.alex.store.core.model;

import lombok.*;
import me.alex.store.core.model.value.BuyOrder;
import me.alex.store.core.model.value.ProductDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class Product {
    @Id
    @Setter(AccessLevel.NONE)
    protected Long id;
    @Version
    @Setter(AccessLevel.NONE)
    protected Long version = 0L;
    protected Long storeRef;
    @Embedded(onEmpty = USE_EMPTY)
    protected ProductDetails productDetails;
    protected Integer availableStock;
    protected Long unitsSold;

    public void increaseStock(Integer quantity) {
        availableStock += quantity;
    }

    public void updateDetails(ProductDetails newDetails) {
        productDetails = new ProductDetails(
                newDetails.getName() == null ? productDetails.getName() : newDetails.getName(),
                newDetails.getDescription() == null ? productDetails.getDescription() : newDetails.getDescription(),
                newDetails.getPrice() == null ? productDetails.getPrice() : newDetails.getPrice()
        );
    }

    public BuyOrder buy(Integer quantity) {
        if (availableStock - quantity < 0) {
            throw new IllegalStateException("Stock is too low for this purchase.");
        }

        availableStock -= quantity;
        unitsSold += quantity;

        return new BuyOrder(productDetails, quantity);
    }
}
