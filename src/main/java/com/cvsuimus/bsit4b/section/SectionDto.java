package com.cvsuimus.bsit4b.section;

public class SectionDto {

  public static record Default(
      Long id,
      String course,
      Integer year,
      String name,
      String gradeColumns,
      String deletedOn,
      Long subjectId,
      Long userId) {

  }
}
