package com.cvsuimus.bsit4b.dto.student;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateStudentDto {
  @NotBlank
  @Size(max = 9)
  private String studentNumber;

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
  private String contactNumber;

  @NotNull
  private Long courseId;
}
