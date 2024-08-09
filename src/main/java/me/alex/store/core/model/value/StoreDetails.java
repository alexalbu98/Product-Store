package me.alex.store.core.model.value;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.relational.core.mapping.Embedded;

import java.io.Serializable;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

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
