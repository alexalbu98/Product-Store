package me.alex.store.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.alex.store.core.model.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private UserRole userRole;
    private String email;
    private String phoneNumber;
}
