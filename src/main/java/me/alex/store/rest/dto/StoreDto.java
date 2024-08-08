package me.alex.store.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String address;
}
