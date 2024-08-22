package me.alex.store.security.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.AppUser;
import me.alex.store.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final var user = userRepository.findByUsername(username);
    final var userDetails = user.map(AppUser::buildUserDetails);
    return userDetails.orElseThrow(() -> new UsernameNotFoundException(
        "The user with username: " + username + " could not be found!"));
  }
}
