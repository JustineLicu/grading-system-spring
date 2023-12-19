package com.cvsuimus.bsit4b.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignInUserDto {

  @NotBlank
  @Size(min = 4, max = 60)
  private String username;

  @NotBlank
  @Size(min = 8, max = 150)
  private String password;
}
