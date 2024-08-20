package me.alex.store.core.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User implements Serializable {

  @Id
  private Long id;
  private String username;
  private String password;
  private UserRole userRole;
  private String email;
  private String phoneNumber;
}
