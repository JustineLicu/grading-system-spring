package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.auth.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "id_number", "deleted_on" }),
    @UniqueConstraint(columnNames = { "email", "deleted_on" }),
    @UniqueConstraint(columnNames = { "username", "deleted_on" }) })
@JsonIgnoreProperties({ "password", "hibernateLazyInitializer" })
public class User {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "id_number", nullable = false)
  private String idNumber;

  @Column(nullable = false)
  private String firstName = "";

  @Column(nullable = false)
  private String middleName = "";

  @Column(nullable = false)
  private String lastName = "";

  @Column(nullable = false)
  private String nameSuffix = "";

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String contactNumber = "";

  @Column(nullable = false)
  private Boolean isActive = false;

  @Column(nullable = false)
  private String role = "instructor";

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id")
  private Department department = null;

  @Column(name = "department_id", insertable = false, updatable = false)
  private Long departmentId;

  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // @JoinColumn(name = "role_name", nullable = false)
  // private Role role;

  // @Column(name = "role_name", nullable = false, insertable = false, updatable =
  // false)
  // private String roleName;

  public User() {
  }

  public User(SignUpUserDto user) {
    idNumber = user.getIdNumber();
    email = user.getEmail();
    username = user.getUsername();
    password = user.getPassword();
  }
}
