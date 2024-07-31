package me.alex.store.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Setter(AccessLevel.NONE)
    protected Long id;
    protected Long storeId;
    protected Long version;
    protected String name;
    protected String description;
    protected Price price;
    protected Integer availableStock;
    protected Long unitsSold;

    public void buy() {
        version += 1;
        availableStock -= 1;
        unitsSold += 1;
    }
}
