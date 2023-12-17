package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table
public class Role {

  @Id
  private String name;

  @Column(nullable = false)
  private String description = "";
}
