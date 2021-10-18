package tacos.ingredientclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class IngredientClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngredientClientApplication.class, args);
	}
}
