package com.cvsuimus.bsit4b.dto.role;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateRoleDto {
  @NotBlank
  @Size(max = 60)
  private String name;

  @Size(max = 150)
  private String description;
}
