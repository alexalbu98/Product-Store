package me.alex.store.core.model.value;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.Value;

import java.io.Serializable;

@Value
@ToString
public class Address implements Serializable {
    @NotBlank
    String country;
    @NotBlank
    String city;
    @NotBlank
    String street;
    @NotBlank
    String zipCode;
}
