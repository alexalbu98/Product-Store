package me.alex.store.rest.dto;

import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.alex.store.core.model.value.Price;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ExistingProductDto extends NewProductDto {

  @Serial
  private static final long serialVersionUID = 1L;

  protected Long productId;

  public ExistingProductDto(Long productId, Long storeId, String name, String description,
      Integer availableStock, Price price) {
    super(storeId, name, description, availableStock, price);
    this.productId = productId;
  }

}
