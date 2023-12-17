package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Subject;

@Repository
public interface SubjectRepository extends HibernateRepository<Subject>, JpaRepository<Subject, Long> {

}
