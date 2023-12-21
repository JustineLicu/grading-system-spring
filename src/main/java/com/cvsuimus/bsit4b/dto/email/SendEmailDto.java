package com.cvsuimus.bsit4b.dto.email;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SendEmailDto {

  @NotBlank
  @Size(max = 150)
  @Email
  private String to;

  @NotBlank
  @Size(max = 60)
  private String subject;

  @NotBlank
  @Size(max = 150)
  private String text;
}
