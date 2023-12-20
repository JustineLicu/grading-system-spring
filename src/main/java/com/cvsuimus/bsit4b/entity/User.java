package com.cvsuimus.bsit4b.entity;

import com.cvsuimus.bsit4b.dto.auth.*;
import com.cvsuimus.bsit4b.dto.user.*;
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
@JsonIgnoreProperties({ "password", "department", "hibernateLazyInitializer" })
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
  private String roleName = "instructor";

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
    isActive = true;
  }

  public User(CreateUserDto user, Department department) {
    idNumber = user.getIdNumber();
    firstName = user.getFirstName();
    middleName = user.getMiddleName() != null ? user.getMiddleName() : middleName;
    lastName = user.getLastName();
    nameSuffix = user.getNameSuffix() != null ? user.getNameSuffix() : nameSuffix;
    email = user.getEmail();
    username = user.getUsername();
    password = user.getPassword();
    contactNumber = user.getContactNumber() != null ? user.getContactNumber() : contactNumber;
    isActive = user.getIsActive() != null ? user.getIsActive() : isActive;
    roleName = user.getRoleName() != null ? user.getRoleName() : roleName;
    this.department = department;
  }

  public User(Long id, UpdateUserDto user, Department department) {
    this.id = id;
    idNumber = user.getIdNumber();
    firstName = user.getFirstName();
    middleName = user.getMiddleName();
    lastName = user.getLastName();
    nameSuffix = user.getNameSuffix();
    email = user.getEmail();
    username = user.getUsername();
    contactNumber = user.getContactNumber();
    isActive = user.getIsActive();
    roleName = user.getRoleName();
    this.department = department;
  }
}
