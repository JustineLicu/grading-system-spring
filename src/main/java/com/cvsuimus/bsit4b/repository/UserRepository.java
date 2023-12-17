package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.User;

@Repository
public interface UserRepository extends HibernateRepository<User>, JpaRepository<User, Long> {

}
