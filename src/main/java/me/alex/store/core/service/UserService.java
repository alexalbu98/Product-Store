package me.alex.store.core.service;

import lombok.RequiredArgsConstructor;
import me.alex.store.core.model.AppUser;
import me.alex.store.core.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;

  public void saveUser(AppUser appUser) {
    if (userRepository.existsByUsername(appUser.getUsername())) {
      throw new IllegalStateException("There exists already a user with this name");
    }

    userRepository.save(appUser);
  }
}
