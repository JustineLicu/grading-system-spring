package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "code", "user_id", "deleted_on" }))
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

  @Column(name = "user_id", insertable = false, updatable = false)
  private Long userId;
}
