package com.cvsuimus.bsit4b.dto.student;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateStudentDto {

  @NotBlank
  @Size(max = 60)
  private String studentNumber;

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
  @Email
  private String email;

  @NotNull
  @Size(max = 30)
  private String contactNumber;

  @NotNull
  private Long courseId;
}
