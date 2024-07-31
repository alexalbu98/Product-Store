package me.alex.store.model;

import lombok.Value;

@Value
public class Price {
    Integer unit;
    Integer subUnit;
    String currency;
}
