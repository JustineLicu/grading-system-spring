package com.cvsuimus.bsit4b.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Section;

@Repository
public interface SectionRepository extends HibernateRepository<Section>, JpaRepository<Section, Long> {

  Optional<Section> findByYearAndNameAndCourseId(Integer year, String name, Long courseId);

  Optional<Section> findByYearAndNameAndCourseIdAndIdNot(Integer year, String name, Long courseId, Long id);
}
