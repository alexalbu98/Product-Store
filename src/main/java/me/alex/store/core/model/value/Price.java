package me.alex.store.core.model.value;

import lombok.Value;

@Value
public class Price {
    Integer unit;
    Integer subUnit;
    String currency;
}
