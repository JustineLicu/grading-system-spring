package com.cvsuimus.bsit4b.dto.department;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateDepartmentDto {
  @NotBlank
  @Size(max = 60)
  private String name;

  @NotNull
  @Size(max = 150)
  private String description;
}
