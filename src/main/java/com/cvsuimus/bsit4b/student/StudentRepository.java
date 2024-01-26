package com.cvsuimus.bsit4b.student;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findByStudentNumberIn(Iterable<String> studentNumbers);

  Optional<Student> findByStudentNumber(String studentNumber);

  Optional<Student> findByEmail(String email);
}
