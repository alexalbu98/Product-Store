package me.alex.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreAdmin {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
