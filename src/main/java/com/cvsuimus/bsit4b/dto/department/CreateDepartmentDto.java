package com.cvsuimus.bsit4b.dto.department;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateDepartmentDto {
  @NotBlank
  @Size(max = 60)
  private String name;

  @Size(max = 150)
  private String description;
}
