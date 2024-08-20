package me.alex.store.core.model;

import static org.springframework.data.relational.core.mapping.Embedded.OnEmpty.USE_EMPTY;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.alex.store.core.model.value.StoreDetails;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("stores")
public class Store implements Serializable {

  @Id
  @Setter(AccessLevel.NONE)
  private Long id;
  @Version
  @Setter(AccessLevel.NONE)
  private Long version = 0L;
  private Long userRef;
  @Embedded(onEmpty = USE_EMPTY)
  private StoreDetails storeDetails;
}
