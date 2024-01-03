package com.cvsuimus.bsit4b.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  Optional<Student> findByStudentNumber(String studentNumber);

  Optional<Student> findByEmail(String email);
}
