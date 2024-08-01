package me.alex.store.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.alex.store.core.repository.UserRepository;
import me.alex.store.rest.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping
    public void register(@Valid UserDto userDto){

    }
}
