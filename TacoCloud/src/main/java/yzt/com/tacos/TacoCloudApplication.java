package yzt.com.tacos;

import yzt.com.tacos.data.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// 声明这是一个SpringBoot应用
// @SpringBootApplication是一个组合注解，组合了3个其他的注解:
//		@SpringBootConfiguration : 声明该类为配置类, 这个注解实际上是 @Configuration 的特殊形式
//		@EnableAutoConfiguration : 启用Spring Boot的自动配置(自动装配和组件扫描)
//		@ComponentScan : 启用组件扫描
public class TacoCloudApplication {

    public static void main(String[] args) {
    // 这个main()方法会调用SpringApplication中的静态的run()方法,

        SpringApplication.run(TacoCloudApplication.class, args);
        /* run()方法会执行应用的引导过程,也就是创建Spring的应用上下文,
        * 在传递给run()方法的两个参数中,一个是配置类(.class),一个是命令行参数(args)
        * */
    }

}
