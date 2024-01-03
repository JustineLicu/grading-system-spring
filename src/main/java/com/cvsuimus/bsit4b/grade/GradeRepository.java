package com.cvsuimus.bsit4b.grade;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

  List<Grade> findBySectionIdAndUserIdAndDeletedOn(Long sectionId, Long userId, String deletedOn);

  List<Grade> findBySectionIdAndUserIdAndDeletedOnNot(
      Long sectionId, Long userId, String deletedOn);

  List<Grade> findByStudentIdAndDeletedOn(Long studentId, String deletedOn);

  List<Grade> findByStudentIdAndDeletedOnNot(Long studentId, String deletedOn);

  Optional<Grade> findBySectionIdAndStudentIdAndDeletedOn(
      Long sectionId, Long studentId, String deletedOn);

  Optional<Grade> findByIdAndUserId(Long id, Long userId);

  @Transactional
  @Modifying
  @Query("update Grade e set e.deletedOn = :deletedOn where e.id = :id and e.userId = :userId")
  int setDeletedOnFor(
      @Param("deletedOn") String deletedOn, @Param("id") Long id, @Param("userId") Long userId);
}
