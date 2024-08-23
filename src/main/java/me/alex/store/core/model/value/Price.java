package me.alex.store.core.model.value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
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
public class Price implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotNull
  private Integer unit;
  @NotNull
  private Integer subUnit;
  @NotBlank
  private String currency;
}
