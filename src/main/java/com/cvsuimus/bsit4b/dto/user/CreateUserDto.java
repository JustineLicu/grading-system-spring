package com.cvsuimus.bsit4b.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateUserDto {

  @NotBlank
  @Size(max = 60)
  private String idNumber;

  @NotBlank
  @Size(max = 60)
  private String firstName;

  @Size(max = 60)
  private String middleName;

  @NotBlank
  @Size(max = 60)
  private String lastName;

  @Size(max = 60)
  private String nameSuffix;

  @NotBlank
  @Size(max = 150)
  private String email;

  @NotBlank
  @Size(min = 4, max = 60)
  private String username;

  @NotNull
  @Size(min = 8, max = 150)
  private String password;

  @Size(max = 30)
  private String contactNumber;

  private Boolean isActive;

  @Size(min = 1, max = 60)
  private String roleName;

  @NotNull
  private Long departmentId;
}
