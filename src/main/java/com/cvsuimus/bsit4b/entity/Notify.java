package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.notify.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Notify {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String subject;

  @Column(nullable = false)
  private String message;

  @Column(nullable = false)
  private String studentId;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String email;

  public Notify(CreateNotifyDto notify, Student student) {
    subject = notify.getSubject();
    message = notify.getMessage();
    this.studentId = studentId;
    fullName = notify.getFullName();
    email = notify.getEmail();
  }

  public Notify(Long id, UpdateNotifyDto notify, Student student) {
    this.id = id;
    subject = notify.getSubject();
    message = notify.getMessage();
    this.studentId = studentId;
    fullName = notify.getFullName();
    email = notify.getEmail();
  }
}
