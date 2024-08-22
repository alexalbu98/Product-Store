package me.alex.store.rest.factory;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.AppUser;
import me.alex.store.core.model.UserRole;
import me.alex.store.rest.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFactory {

  private final PasswordEncoder passwordEncoder;

  public AppUser newUserFromDto(UserDto userDto, UserRole userRole) {
    return new AppUser(null,
        userDto.getUsername(),
        passwordEncoder.encode(userDto.getPassword()),
        userRole,
        userDto.getEmail(),
        userDto.getPhoneNumber());
  }
}
