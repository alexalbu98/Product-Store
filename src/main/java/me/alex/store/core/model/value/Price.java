package me.alex.store.core.model.value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Price implements Serializable {
    @NotNull
    private Integer unit;
    @NotNull
    private Integer subUnit;
    @NotBlank
    private String currency;
}
