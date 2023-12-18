package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.department.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(indexes = @Index(name = "name_index", columnList = "name"))
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Department {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String description = "";

  public Department() {
  }

  public Department(CreateDepartmentDto course) {
    name = course.getName();
    description = course.getDescription() != null ? course.getDescription() : description;
  }

  public Department(Long id, UpdateDepartmentDto course) {
    this.id = id;
    name = course.getName();
    description = course.getDescription();
  }
}
