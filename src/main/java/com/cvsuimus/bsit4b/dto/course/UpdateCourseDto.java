package com.cvsuimus.bsit4b.dto.course;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateCourseDto {

  @NotBlank
  @Size(max = 60)
  private String acronym;

  @NotBlank
  @Size(max = 60)
  private String name;

  @NotBlank
  @Size(max = 150)
  private String description;
}
