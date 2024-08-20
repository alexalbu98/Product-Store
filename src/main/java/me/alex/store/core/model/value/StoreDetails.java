package me.alex.store.core.model.value;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Embedded;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class StoreDetails implements Serializable {

  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @Valid
  @NotNull
  @Embedded(onEmpty = USE_EMPTY, prefix = "address_")
  private Address address;
}
