package me.alex.store.core.model.value;

import lombok.Value;

@Value
public class Address {
    String country;
    String city;
    String street;
    String zipCode;
}
