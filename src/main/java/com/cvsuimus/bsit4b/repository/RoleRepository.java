package com.cvsuimus.bsit4b.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import com.cvsuimus.bsit4b.entity.*;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
