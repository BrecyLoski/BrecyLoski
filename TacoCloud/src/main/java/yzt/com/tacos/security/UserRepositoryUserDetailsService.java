package yzt.com.tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import yzt.com.tacos.User;
import yzt.com.tacos.data.UserRepository;

@Service
// 表明这个类要包含到Spring的组件扫描中, Spring将会自动发现它并将其初始化为一个bean
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    // 将UserRepository注入进来
    public UserRepositoryUserDetailsService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @Override
    // loadUserByUsername()不能返回null
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        User user = userRepo.findByUsername(username);
        if(user != null){
            return user;
        } // user不为空则返回user, 否则返回UsernameNotFoundException
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
