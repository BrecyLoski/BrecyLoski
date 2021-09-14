package com.yzt.tacos.web;

import com.yzt.tacos.Order;
import com.yzt.tacos.Taco;
import com.yzt.tacos.Ingredient;
import com.yzt.tacos.Ingredient.Type;
import com.yzt.tacos.data.IngredientRepository;
import com.yzt.tacos.data.TacoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import javax.validation.Valid;

//@Slf4j
//Lombok提供的注解,在这个类中自动生成一个SLF4J Logger (SLF4J --Simple Logging Facade for Java Logger)
@Controller
// 将这个类识别为控制器
@RequestMapping("/design")
// 指定处理器所处理的请求类型
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    private TacoRepository designRepo;

    @Autowired //构造器 将得到的对象赋值给实例变量
    public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo){
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    // @GetMapping注解声明showDesignFrom()要处理针对"/design"的HTTP GET请求
    // 细化@RequestMapping,指明当接收到"/design"的HTTP GET请求时,调用showDesignForm()来处理请求
    // @GetMapping自Spring 4.3引入,4.3之前使用:
    // @RequestMapping(method = RequestMethod.GET)代替
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(ingredients::add);
        //调用注入的IngredientRepository是findAll(),从数据库获取所有的配料

        Type[] types = Ingredient.Type.values();
        for(Type type : types){ //将获取到的配料过滤成不同类型然后放到模型中
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        return "design";
    }

    @PostMapping
    //注解声明processDesign()要处理HTTP POST请求
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order){
        // @Valid 注解: Spring MVC要对提交的Taco对象进行检查
        // -- 校验在绑定表单数据之后, 调用processDesign()之前
        // 如果存在校验错误,错误信息将会捕获到一个Errors对象,并作为参数传递给processDesign()

        if (errors.hasErrors()){
           return "design";
        } // 如果Errors对象包含错误信息, return "design" -- 即重新呈现design视图

        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type){
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
