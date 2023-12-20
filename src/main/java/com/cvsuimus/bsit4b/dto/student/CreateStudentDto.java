package com.cvsuimus.bsit4b.dto.student;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateStudentDto {

  @NotBlank
  @Size(max = 60)
  private String studentNumber;

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
  @Email
  private String email;

  @Size(max = 30)
  private String contactNumber;

  @NotNull
  private Long courseId;
}
