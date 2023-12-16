package com.cvsuimus.bsit4b.repository;

import org.springframework.data.repository.*;
import org.springframework.data.rest.core.annotation.*;
import com.cvsuimus.bsit4b.entity.*;

@RepositoryRestResource
public interface ZCrudUserRepository extends CrudRepository<User, Long> {

}
