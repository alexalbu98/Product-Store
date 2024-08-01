package me.alex.store.security.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        var userDetails = user.map(u -> User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(u.getUserRole().name())
                .build());
        return userDetails.orElseThrow(() -> new UsernameNotFoundException("The user with username: " + username + " could not be found!"));
    }
}
