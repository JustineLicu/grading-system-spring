package com.cvsuimus.bsit4b.entity;

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
}
