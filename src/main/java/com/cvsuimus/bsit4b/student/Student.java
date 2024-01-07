package com.cvsuimus.bsit4b.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Student {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String studentNumber;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String middleName = "";

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String nameSuffix = "";

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String contactNumber = "";

  @Column(nullable = false)
  private String course;
}
