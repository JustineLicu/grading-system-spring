package com.cvsuimus.bsit4b.dto.section;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateSectionDto {

  @NotNull
  @Max(15)
  private Integer year;

  @NotBlank
  @Size(max = 60)
  private String name;
}
