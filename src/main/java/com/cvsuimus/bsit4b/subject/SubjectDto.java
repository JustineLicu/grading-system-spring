package com.cvsuimus.bsit4b.subject;

public class SubjectDto {

  public static record Default(
      Long id, String code, String description, String deletedOn, Long userId) {

  }
}
