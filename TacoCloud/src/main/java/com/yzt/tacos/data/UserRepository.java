package com.yzt.tacos.data;

import org.springframework.data.repository.CrudRepository;
import com.yzt.tacos.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
  
}
