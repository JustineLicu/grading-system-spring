package com.cvsuimus.bsit4b.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Student;

@Repository
public interface StudentRepository extends HibernateRepository<Student>, JpaRepository<Student, Long> {

  List<Student> findByStudentNumberOrEmail(String studentNumber, String email);

  @Query("select s from Student s where (s.studentNumber = :studentNumber or s.email = :email) and s.id != :id")
  List<Student> findByStudentNumberOrEmailAndIdNot(@Param("studentNumber") String studentNumber,
      @Param("email") String email, @Param("id") Long id);
}
