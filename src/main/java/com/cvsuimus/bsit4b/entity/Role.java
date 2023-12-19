package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.role.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Role {

  @Id
  private String name;

  @Column(nullable = false)
  private String description = "";

  public Role() {
  }

  public Role(CreateRoleDto role) {
    name = role.getName();
    description = role.getDescription() != null ? role.getDescription() : description;
  }

  public Role(String name, UpdateRoleDto role) {
    this.name = name;
    description = role.getDescription();
  }
}
