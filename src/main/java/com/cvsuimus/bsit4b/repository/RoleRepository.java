package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.Role;

@Repository
public interface RoleRepository extends HibernateRepository<Role>, JpaRepository<Role, String> {

}
