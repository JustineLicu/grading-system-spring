package com.cvsuimus.bsit4b.dto.notify;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateNotifyDto {
  @NotBlank
  @Size(max = 60)
  private String subject;

  @NotBlank
  @Size(max = 60)
  private String message;

  @NotBlank
  @Size(max = 60)
  private String fullName;

  @NotBlank
  @Size(max = 60)
  private String email;
}
