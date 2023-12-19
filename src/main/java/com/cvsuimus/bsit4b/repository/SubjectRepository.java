package com.cvsuimus.bsit4b.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Subject;

import jakarta.transaction.Transactional;

@Repository
public interface SubjectRepository extends HibernateRepository<Subject>, JpaRepository<Subject, Long> {

  List<Subject> findByUserIdAndDeletedOn(Long userId, String deletedOn);

  List<Subject> findByUserIdAndDeletedOnNot(Long userId, String deletedOn);

  Optional<Subject> findByCodeAndUserIdAndDeletedOn(String code, Long userId, String deletedOn);

  Optional<Subject> findByCodeAndUserIdAndDeletedOnAndIdNot(String code, Long userId, String deletedOn, Long id);

  @Transactional
  @Modifying
  @Query("update Subject s set s.deletedOn = :deletedOn where s.id = :id")
  int setDeletedOnFor(@Param("deletedOn") String deletedOn, @Param("id") Long id);
}
