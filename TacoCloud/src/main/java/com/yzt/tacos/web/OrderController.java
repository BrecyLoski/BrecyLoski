package com.yzt.tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.yzt.tacos.Order;

@Slf4j
// 在运行期创建一个SLF4J logger对象
@Controller
@RequestMapping(value = "/orders", method = RequestMethod.POST)
// 指明这个控制器的请求处理方法都会处理路径以"/orders"开头的请求
public class OrderController {

    @GetMapping("/current")
    // 处理针对"/orders/current"的HTTP GET请求
    public String OrderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
        // 返回一个名为"orderForm"的逻辑视图名
        // orderForm 视图 由名为 orderForm.html 的 Thyme leaf 模板提供
    }

    @PostMapping("/orders")
    // 处理针对"/orders"的HTTP POST 请求
    public String processOrder(@RequestBody @Validated(value = Order.class) Order order, Errors errors){
        // @Valid 注解: Spring MVC要对提交的Order对象进行检查
        // 如果存在校验错误,错误信息将会捕获到一个Errors对象,并作为参数传递给processOrder()

        if(errors.hasErrors()) {
            return "orderForm";
        } // 检查Errors对象是否包含错误信息

        log.info("Order submitted:" + order);
        return "redirect:/";
        // "redirect:/" --<<Spring in Action>>
    }

}