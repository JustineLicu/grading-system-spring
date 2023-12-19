package com.cvsuimus.bsit4b.dto.subjectsection;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSubjectSectionDto {

  private String gradeColumns;

  @NotNull
  private Long sectionId;
}
