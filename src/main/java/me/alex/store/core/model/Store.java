package me.alex.store.core.model;

import lombok.*;
import me.alex.store.core.model.value.StoreDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @Setter(AccessLevel.NONE)
    private Long id;
    @Version
    @Setter(AccessLevel.NONE)
    private Long version = 0L;
    @Embedded(onEmpty = USE_EMPTY)
    private StoreDetails storeDetails;
}
