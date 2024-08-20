package me.alex.store.core.model.value;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

import jakarta.validation.Valid;
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
public class ProductDetails implements Serializable {

  private String name;
  private String description;
  @Valid
  @Embedded(onEmpty = USE_EMPTY, prefix = "price_")
  private Price price;
}
