package me.alex.store.rest.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StoreDto implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private Long id;
  private String name;
  private String description;
  private String address;
}
