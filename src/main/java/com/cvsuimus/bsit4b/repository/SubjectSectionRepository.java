package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.SubjectSection;

@Repository
public interface SubjectSectionRepository
    extends HibernateRepository<SubjectSection>, JpaRepository<SubjectSection, Long> {

}
