package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Grade;

@Repository
public interface GradeRepository extends HibernateRepository<Grade>, JpaRepository<Grade, Long> {

}
