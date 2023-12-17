package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Section;

@Repository
public interface SectionRepository extends HibernateRepository<Section>, JpaRepository<Section, Long> {

}
