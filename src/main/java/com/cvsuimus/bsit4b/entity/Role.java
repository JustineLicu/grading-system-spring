package com.cvsuimus.bsit4b.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table
public class Role {

  @Id
  private String name;

  @Column(nullable = false)
  private String description = "";
}
