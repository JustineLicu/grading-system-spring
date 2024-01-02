package com.cvsuimus.bsit4b.notify;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table
public class Notify {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String subjectCode;

  @Column(nullable = false)
  private String message;

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private Long studentId;

  @Column(nullable = false)
  private Long userId;

  public NotifyDto.Default toDefaultDto() {
    return new NotifyDto.Default(id, subjectCode, message, fullName, email, studentId, userId);
  }
}
