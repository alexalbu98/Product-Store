package me.alex.store.core.model.value;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Address implements Serializable {

  @NotBlank
  private String country;
  @NotBlank
  private String city;
  @NotBlank
  private String street;
  @NotBlank
  private String zipCode;
}
