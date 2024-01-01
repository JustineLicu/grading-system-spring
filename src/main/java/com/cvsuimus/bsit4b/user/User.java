package com.cvsuimus.bsit4b.user;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id_number", "deleted_on" }),
    @UniqueConstraint(columnNames = { "email", "deleted_on" }),
    @UniqueConstraint(columnNames = { "username", "deleted_on" })
})
public class User implements UserDetails {

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
  private String department = "";

  @Column(nullable = false)
  private Boolean enabled = true;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role = UserRole.INSTRUCTOR;

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.name());
    return Collections.singletonList(authority);
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public UserDto.Default toDefaultDto() {
    return new UserDto.Default(
        id,
        idNumber,
        firstName,
        middleName,
        lastName,
        nameSuffix,
        email,
        username,
        contactNumber,
        department,
        enabled,
        role,
        deletedOn);
  }
}
