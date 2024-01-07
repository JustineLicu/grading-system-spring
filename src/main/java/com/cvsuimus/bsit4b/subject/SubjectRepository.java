package com.cvsuimus.bsit4b.subject;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  List<Subject> findByUserIdAndDeletedOn(Long userId, String deletedOn);

  List<Subject> findByUserIdAndDeletedOnNot(Long userId, String deletedOn);

  Optional<Subject> findByIdAndUserId(Long id, Long userId);

  Optional<Subject> findByCodeAndUserIdAndDeletedOn(String code, Long userId, String deletedOn);

  @Transactional
  @Modifying
  @Query("update Subject e set e.deletedOn = :deletedOn where e.id = :id and e.userId = :userId")
  int setDeletedOnFor(
      @Param("deletedOn") String deletedOn, @Param("id") Long id, @Param("userId") Long userId);
}
