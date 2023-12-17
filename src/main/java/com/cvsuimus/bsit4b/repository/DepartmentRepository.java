package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Department;

@Repository
public interface DepartmentRepository extends HibernateRepository<Department>, JpaRepository<Department, Long> {

}
