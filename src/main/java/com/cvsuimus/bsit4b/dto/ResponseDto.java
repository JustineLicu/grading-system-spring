package com.cvsuimus.bsit4b.dto;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseDto<T> {
  private Boolean success;
  private T data;
  private List<ErrorDto> errors;

  public ResponseDto() {
  }

  public ResponseDto(T data, List<ErrorDto> errors) {
    if (data != null) {
      success = true;
      this.data = data;
    }

    if (errors != null) {
      success = false;
      this.errors = errors;
    }
  }
}
