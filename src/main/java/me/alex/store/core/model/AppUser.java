package me.alex.store.core.model;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class AppUser implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  private Long id;
  private String username;
  private String password;
  private UserRole userRole;
  private String email;
  private String phoneNumber;

  public UserDetails buildUserDetails() {
    return User
        .withUsername(this.getUsername())
        .password(this.getPassword())
        .authorities(this.getUserRole().name())
        .build();
  }
}
