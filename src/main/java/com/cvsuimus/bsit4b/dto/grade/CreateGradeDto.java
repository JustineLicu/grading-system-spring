package com.cvsuimus.bsit4b.dto.grade;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateGradeDto {

  private String gradeRows = "[]";

  private String attendances = "[]";

  @NotNull
  private Long studentId;
}
