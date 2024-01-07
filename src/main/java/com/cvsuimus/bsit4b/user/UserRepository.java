package com.cvsuimus.bsit4b.user;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByIdNumberAndDeletedOn(String idNumber, String deletedOn);

  Optional<User> findByEmailAndDeletedOn(String email, String deletedOn);

  Optional<User> findByUsernameAndDeletedOn(String username, String deletedOn);

  List<User> findByDeletedOn(String deletedOn);

  List<User> findByDeletedOnNot(String deletedOn);

  @Transactional
  @Modifying
  @Query("update User e set e.deletedOn = :deletedOn where e.id = :id")
  int setDeletedOnFor(@Param("deletedOn") String deletedOn, @Param("id") Long id);
}
