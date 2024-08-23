package me.alex.store.rest.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.alex.store.core.model.value.Price;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class NewProductDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull
  protected Long storeId;
  @NotBlank
  protected String name;
  @NotBlank
  protected String description;
  @NotNull
  protected Integer availableStock;
  @NotNull
  protected Price price;
}
