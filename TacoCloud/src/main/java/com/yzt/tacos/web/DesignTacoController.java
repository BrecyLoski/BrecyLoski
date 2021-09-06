package com.yzt.tacos.web;

import com.yzt.tacos.Taco;
import com.yzt.tacos.Ingredient;
import com.yzt.tacos.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
//  Lombok提供的注解,在这个类中自动生成一个SLF4J Logger (SLF4J --Simple Logging Facade for Java)
@Controller
// 将这个类识别为控制器
@RequestMapping("/design")
// 指定处理器所处理的请求类型
public class DesignTacoController {

    @GetMapping
    // @GetMapping注解声明showDesignFrom()要处理针对"/design"的HTTP GET请求
    // 细化@RequestMapping,指明当接收到"/design"的HTTP GET请求时,调用showDesignForm()来处理请求
    // @GetMapping自Spring 4.3引入,4.3之前使用:
    // @RequestMapping(method = RequestMethod.GET)代替
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());

        return "design";
    }

    @PostMapping
    //注解声明processDesign()要处理HTTP POST请求
    public String processDesign(Taco design){

        // Save the taco design
        // We'll do this in chapter 3

        log.info("Processing design:" + design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType( List<Ingredient> ingredients, Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
