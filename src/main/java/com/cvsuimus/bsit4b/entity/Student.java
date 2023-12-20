package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.student.*;
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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @Column(name = "course_id", nullable = false, insertable = false, updatable = false)
  private Long courseId;

  public Student() {
  }

  public Student(CreateStudentDto student, Course course) {
    studentNumber = student.getStudentNumber();
    firstName = student.getFirstName();
    middleName = student.getMiddleName() != null ? student.getMiddleName() : middleName;
    lastName = student.getLastName();
    nameSuffix = student.getNameSuffix() != null ? student.getNameSuffix() : nameSuffix;
    email = student.getEmail();
    contactNumber = student.getContactNumber() != null ? student.getContactNumber() : contactNumber;
    this.course = course;
  }

  public Student(Long id, UpdateStudentDto student, Course course) {
    this.id = id;
    studentNumber = student.getStudentNumber();
    firstName = student.getFirstName();
    middleName = student.getMiddleName();
    lastName = student.getLastName();
    nameSuffix = student.getNameSuffix();
    email = student.getEmail();
    contactNumber = student.getContactNumber();
    this.course = course;
  }
}
