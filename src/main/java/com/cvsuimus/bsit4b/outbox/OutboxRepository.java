package com.cvsuimus.bsit4b.outbox;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

  List<Outbox> findByUserId(Long userId);

  Optional<Outbox> findByIdAndUserId(Long id, Long userId);

  @Transactional
  @Modifying
  @Query("delete from Outbox e where e.sectionId = :sectionId")
  void deleteBySectionId(@Param("sectionId") Long sectionId);

  @Transactional
  @Modifying
  @Query("delete from Outbox e where e.userId = :userId")
  void deleteByUserId(@Param("userId") Long userId);

  @Transactional
  @Modifying
  @Query("delete from Outbox e where e.id = :id and e.userId = :userId")
  void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
