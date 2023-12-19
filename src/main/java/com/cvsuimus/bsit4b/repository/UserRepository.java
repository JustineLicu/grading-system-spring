package com.cvsuimus.bsit4b.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.User;

@Repository
public interface UserRepository extends HibernateRepository<User>, JpaRepository<User, Long> {

  List<User> findByIdNumberOrEmailOrUsername(String idNumber, String email, String username);
}
