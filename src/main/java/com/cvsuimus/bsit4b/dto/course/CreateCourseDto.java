package com.cvsuimus.bsit4b.dto.course;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCourseDto {

  @NotBlank
  @Size(max = 60)
  private String acronym;

  @NotBlank
  @Size(max = 60)
  private String name;

  @Size(max = 150)
  private String description;
}
