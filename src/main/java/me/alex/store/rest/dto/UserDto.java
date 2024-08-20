package me.alex.store.rest.dto;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDto implements Serializable {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  @NotBlank
  private String email;
  @NotBlank
  private String phoneNumber;
}
