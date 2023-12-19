package com.cvsuimus.bsit4b.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.SubjectSection;

import jakarta.transaction.Transactional;

@Repository
public interface SubjectSectionRepository
    extends HibernateRepository<SubjectSection>, JpaRepository<SubjectSection, Long> {

  List<SubjectSection> findBySubjectIdAndDeletedOn(Long subjectId, String deletedOn);

  List<SubjectSection> findBySubjectIdAndDeletedOnNot(Long subjectId, String deletedOn);

  Optional<SubjectSection> findBySubjectIdAndSectionIdAndDeletedOn(Long subjectId, Long sectionId, String deletedOn);

  Optional<SubjectSection> findBySubjectIdAndSectionIdAndDeletedOnAndIdNot(Long subjectId, Long sectionId,
      String deletedOn, Long id);

  @Transactional
  @Modifying
  @Query("update SubjectSection ss set ss.deletedOn = :deletedOn where ss.id = :id")
  int setDeletedOnFor(@Param("deletedOn") String deletedOn, @Param("id") Long id);
}
