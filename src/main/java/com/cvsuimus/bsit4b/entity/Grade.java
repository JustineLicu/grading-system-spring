package com.cvsuimus.bsit4b.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "subject_section_id", "student_id", "deleted_on" }))
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Grade {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @Column(nullable = false, columnDefinition = "longtext")
  private String gradeRows = "[]";

  @Column(nullable = false, columnDefinition = "longtext")
  private String attendances = "[]";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_section_id", nullable = false)
  private SubjectSection subjectSection;

  @Column(name = "subject_section_id", nullable = false, insertable = false, updatable = false)
  private Long subjectSectionId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(name = "student_id", nullable = false, insertable = false, updatable = false)
  private Long studentId;
}
