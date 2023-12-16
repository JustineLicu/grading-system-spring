package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "subject_id", "section_id", "deleted_on" }))
public class SubjectSection {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @Column(nullable = false)
  private String gradeColumns = "[]";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private Subject subject;

  @Column(name = "subject_id", insertable = false, updatable = false)
  private Long subjectId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "section_id", nullable = false)
  private Course section;

  @Column(name = "section_id", insertable = false, updatable = false)
  private Long sectionId;
}
