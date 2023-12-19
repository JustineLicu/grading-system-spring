package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.subjectsection.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "subject_id", "section_id", "deleted_on" }))
@JsonIgnoreProperties({ "subject", "section", "hibernateLazyInitializer" })
public class SubjectSection {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @Column(nullable = false, columnDefinition = "longtext")
  private String gradeColumns = "[]";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private Subject subject;

  @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
  private Long subjectId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "section_id", nullable = false)
  private Section section;

  @Column(name = "section_id", nullable = false, insertable = false, updatable = false)
  private Long sectionId;

  public SubjectSection() {
  }

  public SubjectSection(CreateSubjectSectionDto subjectSection, Subject subject, Section section) {
    gradeColumns = subjectSection.getGradeColumns() != null ? subjectSection.getGradeColumns() : gradeColumns;
    this.subject = subject;
    this.section = section;
  }

  public SubjectSection(Long id, UpdateSubjectSectionDto subjectSection, Subject subject, Section section) {
    this.id = id;
    gradeColumns = subjectSection.getGradeColumns();
    this.subject = subject;
    this.section = section;
  }
}
