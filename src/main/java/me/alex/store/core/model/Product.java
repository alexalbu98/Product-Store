package me.alex.store.core.model;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

import java.io.Serial;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.alex.store.core.model.value.ProductDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("products")
public class Product implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @Setter(AccessLevel.NONE)
  protected Long id;
  @Version
  @Setter(AccessLevel.NONE)
  protected Long version = 0L;
  protected Long storeRef;
  @Embedded(onEmpty = USE_EMPTY)
  protected ProductDetails productDetails;
  protected Integer availableStock;
  protected Long unitsSold;

  public void increaseStock(Integer quantity) {
    availableStock += quantity;
  }

  public void updateDetails(ProductDetails newDetails) {
    productDetails = new ProductDetails(
        newDetails.getName() == null ? productDetails.getName() : newDetails.getName(),
        newDetails.getDescription() == null ? productDetails.getDescription()
            : newDetails.getDescription(),
        newDetails.getPrice() == null ? productDetails.getPrice() : newDetails.getPrice());
  }

  public void buy(Integer quantity) {
    if (availableStock - quantity < 0) {
      throw new IllegalStateException("Stock is too low for this purchase.");
    }

    availableStock -= quantity;
    unitsSold += quantity;
  }
}
