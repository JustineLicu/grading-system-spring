package com.cvsuimus.bsit4b.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateUserDto {
  @NotBlank
  @Size(max = 60)
  private String idNumber;

  @NotBlank
  @Size(max = 60)
  private String firstName;

  @NotBlank
  @Size(max = 60)
  private String middleName;

  @NotBlank
  @Size(max = 60)
  private String lastName;

  @NotBlank
  @Size(max = 20)
  private String nameSuffix;

  @NotBlank
  @Size(max = 60)
  private String email;

  @NotBlank
  @Size(max = 60)
  private String username;

  @NotBlank
  @Size(max = 60)
  private String password;

  @NotBlank
  @Size(max = 60)
  private String contactNumber;

  @NotBlank
  @Size(max = 60)
  private String role;

  @NotBlank
  @Size(max = 60)
  private String deletedOn;

  @NotNull
  private Long departmentId;
}
