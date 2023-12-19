package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.subject.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "code", "user_id", "deleted_on" }))
@JsonIgnoreProperties({ "user", "hibernateLazyInitializer" })
public class Subject {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String description;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private Long userId;

  public Subject() {
  }

  public Subject(CreateSubjectDto subject, User user) {
    code = subject.getCode();
    description = subject.getDescription();
    this.user = user;
  }

  public Subject(Long id, UpdateSubjectDto subject, User user) {
    this.id = id;
    code = subject.getCode();
    description = subject.getDescription();
    this.user = user;
  }
}
