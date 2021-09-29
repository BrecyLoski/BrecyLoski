package yzt.com.tacos.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import yzt.com.tacos.User;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    // 创建一个新的User对象
    public User toUser(PasswordEncoder passwordEncoder){
        // 在将密码保存到数据库之前,会使用PasswordEncoder对密码进行转码
        return new User(username, passwordEncoder.encode(password),
                        fullname, street, city, state, zip, phone);
    }
}
