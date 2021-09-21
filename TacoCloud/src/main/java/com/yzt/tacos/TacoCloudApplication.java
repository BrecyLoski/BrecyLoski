package com.yzt.tacos;

import com.yzt.tacos.data.IngredientRepository;
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

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
            }
        };
    }
}
