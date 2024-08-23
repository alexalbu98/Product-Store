package me.alex.store.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.alex.store.core.model.value.ProductDetails;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateProductDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull
  private Long productId;
  @Valid
  @NotNull
  private ProductDetails productDetails;
}
