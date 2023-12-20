package com.cvsuimus.bsit4b.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Grade;

import jakarta.transaction.Transactional;

@Repository
public interface GradeRepository extends HibernateRepository<Grade>, JpaRepository<Grade, Long> {

  List<Grade> findBySubjectSectionIdAndDeletedOn(Long subjectSectionId, String deletedOn);

  List<Grade> findBySubjectSectionIdAndDeletedOnNot(Long subjectSectionId, String deletedOn);

  Optional<Grade> findBySubjectSectionIdAndStudentIdAndDeletedOn(Long subjectSectionId, Long studentId,
      String deletedOn);

  Optional<Grade> findBySubjectSectionIdAndStudentIdAndDeletedOnAndIdNot(Long subjectSectionId, Long studentId,
      String deletedOn, Long id);

  @Transactional
  @Modifying
  @Query("update Grade g set g.deletedOn = :deletedOn where g.id = :id")
  int setDeletedOnFor(@Param("deletedOn") String deletedOn, @Param("id") Long id);
}
