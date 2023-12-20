package com.cvsuimus.bsit4b.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cvsuimus.bsit4b.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends HibernateRepository<User>, JpaRepository<User, Long> {

  List<User> findByDeletedOn(String deletedOn);

  List<User> findByDeletedOnNot(String deletedOn);

  Optional<User> findByUsernameAndPassword(String username, String password);

  List<User> findByIdNumberOrEmailOrUsername(String idNumber, String email, String username);

  @Query("select u from User u where (u.idNumber = :idNumber or u.email = :email or u.username = :username) and u.id != :id")
  List<User> findByIdNumberOrEmailOrUsernameAndIdNot(@Param("idNumber") String idNumber, @Param("email") String email,
      @Param("username") String username, @Param("id") Long id);

  @Transactional
  @Modifying
  @Query("update User u set u.deletedOn = :deletedOn where u.id = :id")
  int setDeletedOnFor(@Param("deletedOn") String deletedOn, @Param("id") Long id);
}
