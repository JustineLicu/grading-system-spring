package com.cvsuimus.bsit4b.notify;

public class NotifyDto {

  public static record Default(
      Long id,
      String subjectCode,
      String message,
      String fullName,
      String email,
      Long studentId,
      Long userId) {

  }
}
