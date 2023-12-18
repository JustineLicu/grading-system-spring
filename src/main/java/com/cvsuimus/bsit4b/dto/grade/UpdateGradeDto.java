package com.cvsuimus.bsit4b.dto.grade;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateGradeDto {
  @NotBlank
  @Size(max = 60)
  private String deletedOn;

  @NotBlank
  private String gradeRows;

  @NotBlank
  private String attendances;

  @NotNull
  private Long subjectSectionId;

  @NotNull
  private Long stundentId;
}
