package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.course.*;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(indexes = @Index(name = "acronym_index", columnList = "acronym"))
public class Course {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String acronym;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description = "";

  public Course() {
  }

  public Course(CreateCourseDto course) {
    acronym = course.getAcronym();
    name = course.getName();
    description = course.getDescription() != null ? course.getDescription() : description;
  }

  public Course(Long id, UpdateCourseDto course) {
    this.id = id;
    acronym = course.getAcronym();
    name = course.getName();
    description = course.getDescription();
  }
}
