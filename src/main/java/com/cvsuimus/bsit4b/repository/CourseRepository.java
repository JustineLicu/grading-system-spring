package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Course;

@Repository
public interface CourseRepository extends HibernateRepository<Course>, JpaRepository<Course, Long> {

}
