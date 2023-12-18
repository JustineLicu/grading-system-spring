package com.cvsuimus.bsit4b.dto.subjectsection;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSubjectSectionDto {
  @NotBlank
  @Size(max = 60)
  private String deletedOn;

  @NotBlank
  private String gradeColumns;

  @NotNull
  private Long subjectId;

  @NotNull
  private Long sectionId;
}
