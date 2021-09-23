package yzt.com.tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 自动装配
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{

        // 基于内存的用户存储
            // 用inMemoryAuthentication()指定用户信息
        auth.inMemoryAuthentication()
                .withUser("bryce") // 给定 用户名
                    .password("{noop}TacoCloud") // 指定 密码存储格式为{noop}的密码
                    .authorities("ROLE_USER") // 授权信息
                .and()
                .withUser("loski")
                    .password("{noop}TacoCloud") // {noop}
                    .authorities("ROLE_USER");
        /*
        * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id “null”
        * 在Spring Security 5.0之前，默认值PasswordEncoder是NoOpPasswordEncoder需要纯文本密码。
        * 在Spring Security 5.o以后，默认值PasswordEncoder是DelegatingPasswordEncoder，这需要密码存储格式。
        * */


        // 基于JDBC的用户存储
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from Users "
                        + "where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from UserAuthorities"
                        + "where username = ?")
                .passwordEncoder(new StandardPasswordEncoder("TacoCloud"));
                // 借助passwordEncoder()指定一个密码转码器(这里使用SHA-256哈希加密)
    }
}
