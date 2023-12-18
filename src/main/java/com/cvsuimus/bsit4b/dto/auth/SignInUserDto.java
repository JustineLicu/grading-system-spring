package com.cvsuimus.bsit4b.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignInUserDto {

  @NotBlank
  @Size(max = 60)
  private String username;

  @NotBlank
  @Size(max = 150)
  private String password;
}
