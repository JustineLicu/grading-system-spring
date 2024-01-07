package com.cvsuimus.bsit4b.outbox;

import com.cvsuimus.bsit4b.section.Section;
import com.cvsuimus.bsit4b.student.Student;
import com.cvsuimus.bsit4b.user.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table
public class Outbox {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String emailSubject;

  @Column(nullable = false)
  private String message;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "section_id", nullable = false)
  private Section section;

  @Column(name = "section_id", nullable = false, insertable = false, updatable = false)
  private Long sectionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
  private Long studentId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private Long userId;

  public OutboxDto.Default toDefaultDto() {
    return new OutboxDto.Default(id, emailSubject, message, sectionId, studentId, userId);
  }

  public OutboxDto.FetchStudent toFetchStudentDto() {
    return new OutboxDto.FetchStudent(
        id, emailSubject, message, sectionId, studentId, student, userId);
  }
}
