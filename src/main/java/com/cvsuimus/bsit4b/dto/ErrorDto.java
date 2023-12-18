package com.cvsuimus.bsit4b.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorDto {
  private String name;
  private String message;

  public ErrorDto() {
  }

  public ErrorDto(String name, String message) {
    this.name = name;
    this.message = message;
  }
}
