package yzt.com.tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import org.springframework.security.core.userdetails.UserDetailsService;
import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired // 自动装配
    //DataSource dataSource;
    private UserDetailsService userDetailsService;

    @Override
    // 配置HttpSecurity  ---拦截请求以确保用户具备适当的权限
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                /* 返回一个对象(ExpressionInterceptUrlRegisty)
                 * 基于此对象可以指定URL路径和这些路径的安全需求
                 * */
                .antMatchers("/design", "/orders")// 确保只有认证过的用户(具有ROLE_USER权限),才能发起对"/design","/orders"的请求
                .access("hasRole('ROLE_USER')")// 使用access()方法,通过为其提供SpEL表达式来声明更丰富的安全规则
                .antMatchers("/", "/**")// 其它请求对所有用户均可用
                .access("permitAll")

                .and() // 返回一个HttpSecurityBuilder对象
                // and()表明完成授权相关的配置,并要添加一些其它的HTTP配置
                .formLogin() // 替换默认的登录页
                .loginPage("/login") // 声明自定义登录页的路径
                // 当Spring Security断定用户没有认证并且需要认证的时候,它会将用户重定向到该路径
                .defaultSuccessUrl("/design", true)
                // 指定默认的成功页, true --> 无论任何页面,登录后都跳转到"/design"

                .and()
                .logout()
                .logoutSuccessUrl("/login")
                // 指定默认退出登录的路径, session将会被清理

                // Make H2-Console non-secured; for debug purposes
                .and()
                .csrf()
                .ignoringAntMatchers("/h2-console/**")

                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }

    @Bean
    // 声明一个PasswordEncoder类型的Bean
    public PasswordEncoder encoder(){
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{

        /* encoder()带有@Bean注解,
         * 它将用来在Spring应用上下文中声明PasswordEncoder bean,
         * 对于encoder()的任何调用都会被拦截,并且返回应用上下文的bean实例
         * */
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());


        //        // 基于内存的用户存储
//        auth.inMemoryAuthentication() // 用inMemoryAuthentication()指定用户信息
//                .withUser("bryce") // 给定 用户名
//                    .password("{noop}TacoCloud") // 指定 密码存储格式为{noop}的密码
//                    .authorities("ROLE_USER") // 授权信息
//                .and()
//                .withUser("loski")
//                    .password("{noop}TacoCloud") // {noop}
//                    .authorities("ROLE_USER");
//        /*
//        * java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id “null”
//        * 在Spring Security 5.0之前，默认值PasswordEncoder是NoOpPasswordEncoder需要纯文本密码。
//        * 在Spring Security 5.o以后，默认值PasswordEncoder是DelegatingPasswordEncoder，这需要密码存储格式。
//        * */
//
//
//        // 基于JDBC的用户存储
//        auth.jdbcAuthentication()
//                .dataSource(dataSource) // 通过自动装配指定连接数据库
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users "
//                        + "where username = ?") // 通过用户名查询用户
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities"
//                        + "where username = ?") // 通过用户名查询用户权限
//                .passwordEncoder(new StandardPasswordEncoder("TacoCloud"));
//                // 借助passwordEncoder()指定一个密码转码器(这里使用SHA-256哈希加密)
//
//        // 基于LDAP作为后端的用户存储
//        auth.ldapAuthentication()
//                .userSearchBase("ou=people") // 为查找用户指定基础查询 (在名为people的组织单元下搜索)
//                .userSearchFilter("uid={0}") // 搜索用户的过滤条件
//                .groupSearchBase("ou=groups") // 为查找组指定基础查询 (在名为groups的组织单元下搜索)
//                .groupSearchFilter("member={0}") // 搜索组的过滤条件
//                .passwordCompare() // 认证策略采用比对认证 (另一种'默认'策略是采用绑定操作认证)
//                .passwordEncoder(new BCryptPasswordEncoder()) // 指定密码转码器
//                .passwordAttribute("passcode") // 声明密码属性的名称 (默认是用户的LDAP条目中的userPassword属性)
//                .and() // .and()返回LdapAuthenticationProviderConfigurer对象
//                    .contextSource() // contextSource()返回一个ContextSourceBuilder对象
//                        // .url("ldap://tacocloud.com:389/dc=tacocloud, dc=com"); // 指定远程LDAP服务器地址
//                        .root("dc=tacocloud,dc=com") // 指定嵌入式LDAP服务器
//                        .ldif("classpath:users.ldif"); // 指定加载LDIF文件

        // 将自定义的用户详情服务与Spring Security配置在一起
    }
}
