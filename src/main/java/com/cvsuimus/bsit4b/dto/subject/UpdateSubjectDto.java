package com.cvsuimus.bsit4b.dto.subject;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateSubjectDto {
  @NotBlank
  @Size(max = 60)
  private String code;

  @NotBlank
  @Size(max = 150)
  private String description;

  @NotBlank
  @Size(max = 60)
  private String deletedOn;

  @NotNull
  private Long userId;
}
