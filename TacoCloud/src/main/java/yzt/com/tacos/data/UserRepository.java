package yzt.com.tacos.data;

import org.springframework.data.repository.CrudRepository;
import yzt.com.tacos.User;

public interface UserRepository extends CrudRepository<User, Long> {

    // 根据用户名查找User
    User findByUsername(String username);
}
