package com.cvsuimus.bsit4b.notify;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotifyRepository extends JpaRepository<Notify, Long> {

  List<Notify> findByUserId(Long userId);
}
