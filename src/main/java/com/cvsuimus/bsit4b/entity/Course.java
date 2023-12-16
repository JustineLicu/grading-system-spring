package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
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
}
