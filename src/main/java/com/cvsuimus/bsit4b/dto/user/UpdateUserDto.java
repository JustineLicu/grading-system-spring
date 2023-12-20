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

  @NotNull
  @Size(max = 60)
  private String middleName;

  @NotBlank
  @Size(max = 60)
  private String lastName;

  @NotNull
  @Size(max = 60)
  private String nameSuffix;

  @NotBlank
  @Size(max = 150)
  private String email;

  @NotBlank
  @Size(min = 4, max = 60)
  private String username;

  @NotNull
  @Size(max = 30)
  private String contactNumber;

  // @NotNull
  private Boolean isActive = true;

  // @NotBlank
  @Size(max = 60)
  private String roleName = "instructor";

  // @NotNull
  private Long departmentId = null;
}
