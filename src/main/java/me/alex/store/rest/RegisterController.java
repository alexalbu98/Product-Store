package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.UserRole;
import me.alex.store.core.service.UserService;
import me.alex.store.rest.dto.UserDto;
import me.alex.store.rest.factory.UserFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
public class RegisterController {

  private final UserFactory userFactory;
  private final UserService userService;

  @PostMapping("/client")
  public String registerClient(@Valid @RequestBody UserDto userDto) {
    var user = userFactory.newUserFromDto(userDto, UserRole.CLIENT);
    userService.saveUser(user);

    return "Client created successfully";
  }

  @PostMapping("/owner")
  public String registerOwner(@Valid @RequestBody UserDto userDto) {
    var user = userFactory.newUserFromDto(userDto, UserRole.OWNER);
    userService.saveUser(user);

    return "Store owner created successfully";
  }
}
