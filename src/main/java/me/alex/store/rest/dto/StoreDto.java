package me.alex.store.rest.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StoreDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String address;
}
