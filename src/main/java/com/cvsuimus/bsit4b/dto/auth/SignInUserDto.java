package com.cvsuimus.bsit4b.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignInUserDto {

  @NotBlank
  @Size(min = 4, max = 60)
  private String username;

  @NotNull
  @Size(min = 8, max = 150)
  private String password;
}
