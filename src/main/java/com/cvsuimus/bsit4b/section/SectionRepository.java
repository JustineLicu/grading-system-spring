package com.cvsuimus.bsit4b.section;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

  List<Section> findBySubjectIdAndUserIdAndDeletedOn(Long subjectId, Long userId, String deletedOn);

  List<Section> findBySubjectIdAndUserIdAndDeletedOnNot(
      Long subjectId, Long userId, String deletedOn);

  Optional<Section> findByIdAndUserId(Long id, Long userId);

  Optional<Section> findByCourseAndYearAndNameAndSubjectIdAndDeletedOn(
      String course, Integer year, String name, Long subjectId, String deletedOn);

  @Transactional
  @Modifying
  @Query("update Section e set e.deletedOn = :deletedOn where e.id = :id and e.userId = :userId")
  int setDeletedOnFor(
      @Param("deletedOn") String deletedOn, @Param("id") Long id, @Param("userId") Long userId);
}
