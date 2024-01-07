package com.cvsuimus.bsit4b.outbox;

import java.util.List;

import com.cvsuimus.bsit4b.student.Student;

public class OutboxDto {

  public static record Default(
      Long id,
      String emailSubject,
      String message,
      Long sectionId,
      Long studentId,
      Long userId) {

  }

  public static record FetchStudent(
      Long id,
      String emailSubject,
      String message,
      Long sectionId,
      Long studentId,
      Student student,
      Long userId) {

  }

  public static record CreateMany(Long sectionId, List<Outbox> outbox) {

  }
}
