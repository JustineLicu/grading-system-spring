package com.cvsuimus.bsit4b.dto.section;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSectionDto {

  @NotNull
  @Min(1)
  @Max(11)
  private Integer year;

  @NotBlank
  @Size(max = 60)
  private String name;

  @NotNull
  private Long courseId;
}
