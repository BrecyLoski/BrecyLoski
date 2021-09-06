package com.yzt.tacos.web;

import javax.validation.Valid;

import com.yzt.tacos.Taco;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import com.yzt.tacos.Order;

@Slf4j
// 在运行期创建一个SLF4J logger对象
@Controller
@RequestMapping("/orders")
// 指明这个控制器的请求处理方法都会处理路径以"/orders"开头的请求
public class orderController {

    @PostMapping("/orders")
    // 处理针对"/orders"的HTTP POST 请求
    public String processOrder(Order order){
        log.info("Order submitted:" + order);
        return "redirect:/orders/current";
        // "redirect:/" --<<Spring in Action>>
        // 修改成"redirect:/orders/current"避免HTTP 404
    }

    @GetMapping("/current")
    // 处理针对"/orders/current"的HTTP GET请求
    public String orderForm(Model model){
        model.addAttribute("order", new Order());
        return "orderForm";
        // 返回一个名为"orderForm"的逻辑视图名
        // orderForm 视图 由名为 orderForm.html 的 Thyme leaf 模板提供
    }

}
