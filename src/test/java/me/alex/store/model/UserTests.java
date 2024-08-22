package me.alex.store.model;

import static me.alex.store.TestData.testClientUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import me.alex.store.core.model.AppUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTests {

  @Test
  @DisplayName("Build spring security user details from user")
  void build_user_details() {
    AppUser user = testClientUser();
    var details = testClientUser().buildUserDetails();

    assertEquals(user.getPassword(), details.getPassword());
    assertEquals(user.getUsername(), details.getUsername());
    assertTrue(details.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals(user.getUserRole().name())));
  }

}
