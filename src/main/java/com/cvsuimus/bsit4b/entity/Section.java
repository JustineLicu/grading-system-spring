package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "year", "name",
    "course_id" }), indexes = @Index(name = "ync_index", columnList = "year, name, course_id"))
public class Section {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Integer year;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "course_id", insertable = false, updatable = false)
  private Long courseId;
}
