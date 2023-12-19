package com.cvsuimus.bsit4b.dto.auth;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SignUpUserDto {

  @NotBlank
  @Size(max = 60)
  private String idNumber;

  @NotBlank
  @Size(max = 150)
  @Email
  private String email;

  @NotBlank
  @Size(min = 4, max = 60)
  private String username;

  @NotNull
  @Size(min = 8, max = 150)
  private String password;
}
