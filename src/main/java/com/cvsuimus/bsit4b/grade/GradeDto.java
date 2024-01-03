package com.cvsuimus.bsit4b.grade;

import com.cvsuimus.bsit4b.section.SectionDto;
import com.cvsuimus.bsit4b.student.Student;

public class GradeDto {

  public static record Default(
      Long id,
      String gradeRows,
      String attendances,
      String deletedOn,
      Long sectionId,
      Long studentId,
      Long userId) {

  }

  public static record FetchSection(
      Long id,
      String gradeRows,
      String attendances,
      String deletedOn,
      Long sectionId,
      SectionDto.Default section,
      Long studentId,
      Long userId) {

  }

  public static record FetchStudent(
      Long id,
      String gradeRows,
      String attendances,
      String deletedOn,
      Long sectionId,
      Long studentId,
      Student student,
      Long userId) {

  }

  public static record FetchSectionAndStudent(
      Long id,
      String gradeRows,
      String attendances,
      String deletedOn,
      Long sectionId,
      SectionDto.Default section,
      Long studentId,
      Student student,
      Long userId) {

  }
}
