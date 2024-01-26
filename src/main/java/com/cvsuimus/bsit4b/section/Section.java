package com.cvsuimus.bsit4b.section;

import java.util.*;

import com.cvsuimus.bsit4b.student.Student;
import com.cvsuimus.bsit4b.subject.Subject;
import com.cvsuimus.bsit4b.user.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
    "course", "section_year", "name", "subject_id", "deleted_on"
}))
public class Section {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String course;

  @Column(name = "section_year", nullable = false)
  private Integer year;

  @Column(nullable = false)
  private String name;

  // @Column(nullable = false, columnDefinition = "longtext") // TODO: For MySQL
  @Column(nullable = false)
  @Lob
  private String gradeColumns = "[]";

  @Transient
  private List<Student> students = new ArrayList<Student>();

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "subject_id", nullable = false)
  private Subject subject;

  @Column(name = "subject_id", nullable = false, insertable = false, updatable = false)
  private Long subjectId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private Long userId;

  public SectionDto.Default toDefaultDto() {
    return new SectionDto.Default(
        id, course, year, name, gradeColumns, deletedOn, subjectId, userId);
  }
}
