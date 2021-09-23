package yzt.com.tacos.web;


import javax.validation.Valid;

import yzt.com.tacos.data.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import yzt.com.tacos.Order;
import org.springframework.web.bind.support.SessionStatus;


//@Slf4j
// 在运行期创建一个SLF4J logger对象
@Controller
@RequestMapping(value = "/orders")
// 指明这个控制器的请求处理方法都会处理路径以"/orders"开头的请求
@SessionAttributes("order")
// 指定模型对象(如订单属性)要保存在session中, 这样才能跨请求使用
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo){
        this.orderRepo = orderRepo;
    }

    @GetMapping(value = "/current")
    // 处理针对"/orders/current"的HTTP GET请求
    public String orderForm(Model model){
        //model.addAttribute("order", new Order());
        return "orderForm";
        // 返回一个名为"orderForm"的逻辑视图名
        // orderForm 视图 由名为 orderForm.html 的 Thymeleaf 模板提供
    }

    @PostMapping
    // 处理针对"/orders"的HTTP POST 请求
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus){
        // @Valid 注解: Spring MVC要对提交的Order对象进行检查
        // 如果存在校验错误,错误信息将会捕获到一个Errors对象,并作为参数传递给processOrder()

        if(errors.hasErrors()) {
            return "orderForm";
        } // 检查Errors对象是否包含错误信息

        //log.info("Order submitted:" + order);
        orderRepo.save(order);
        // 表单提交的Order对象(同时也session中持有的Object对象)通过注入的OrderRepository的save()保存
        sessionStatus.setComplete();
        // 调用SessionStatus的setComplete()方法重置session
        return "redirect:/";
        // "redirect:/" --<<Spring in Action>>
    }

}
