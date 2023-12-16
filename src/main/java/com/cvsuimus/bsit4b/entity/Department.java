package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(indexes = @Index(name = "name_index", columnList = "name"))
public class Department {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description = "";
}
