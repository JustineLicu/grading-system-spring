package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "subject_section_id", "student_id", "deleted_on" }))
public class Grade {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @Column(nullable = false)
  private String gradeRows = "[]";

  @Column(nullable = false)
  private String attendances = "[]";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_section_id", nullable = false)
  private SubjectSection subjectSection;

  @Column(name = "subject_section_id", insertable = false, updatable = false)
  private Long subjectSectionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(name = "student_id", insertable = false, updatable = false)
  private Long studentId;
}
