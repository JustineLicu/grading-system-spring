package com.cvsuimus.bsit4b.subject;

import java.util.*;

import com.cvsuimus.bsit4b.section.Section;
import com.cvsuimus.bsit4b.user.User;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "subjects", uniqueConstraints = @UniqueConstraint(columnNames = {
    "code", "user_id", "deleted_on"
}))
public class Subject {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String code;

  @Column(nullable = false)
  private String description;

  @Transient
  private List<Section> sections = new ArrayList<Section>();

  @Column(name = "deleted_on", nullable = false)
  private String deletedOn = "";

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private Long userId;

  public SubjectDto.Default toDefaultDto() {
    return new SubjectDto.Default(id, code, description, deletedOn, userId);
  }
}
