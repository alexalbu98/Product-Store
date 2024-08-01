package me.alex.store.core.model.value;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class Address {
    @NotBlank
    String country;
    @NotBlank
    String city;
    @NotBlank
    String street;
    @NotBlank
    String zipCode;
}
