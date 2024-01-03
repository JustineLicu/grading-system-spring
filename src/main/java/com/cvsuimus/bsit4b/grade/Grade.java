package com.cvsuimus.bsit4b.grade;

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
// (uniqueConstraints = @UniqueConstraint(columnNames = {
// "section_id", "student_id", "deleted_on"
// }))
public class Grade {

  @Id
  @GeneratedValue
  private Long id;

  // @Column(nullable = false, columnDefinition = "longtext") // TODO: For MySQL
  @Column(nullable = false)
  @Lob
  private String gradeRows = "[]";

  // @Column(nullable = false, columnDefinition = "longtext") // TODO: For MySQL
  @Column(nullable = false)
  @Lob
  private String attendances = "[]";

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

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

  public GradeDto.Default toDefaultDto() {
    return new GradeDto.Default(
        id, gradeRows, attendances, deletedOn, sectionId, studentId, userId);
  }

  public GradeDto.FetchSection toFetchSectionDto() {
    return new GradeDto.FetchSection(
        id,
        gradeRows,
        attendances,
        deletedOn,
        sectionId,
        section.toDefaultDto(),
        studentId,
        userId);
  }

  public GradeDto.FetchStudent toFetchStudentDto() {
    return new GradeDto.FetchStudent(
        id, gradeRows, attendances, deletedOn, sectionId, studentId, student, userId);
  }

  public GradeDto.FetchSectionAndStudent toFetchSectionAndStudentDto() {
    return new GradeDto.FetchSectionAndStudent(
        id,
        gradeRows,
        attendances,
        deletedOn,
        sectionId,
        section.toDefaultDto(),
        studentId,
        student,
        userId);
  }
}
