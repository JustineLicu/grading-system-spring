package com.cvsuimus.bsit4b.dto.role;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateRoleDto {
  @NotNull
  @Size(max = 150)
  private String description;
}
