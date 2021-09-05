package com.yzt.tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 控制器
public class HomeController {

    @GetMapping("/") // 处理对根路径“/“的请求
    public String home(){ // 控制器方法
        return "home"; // 返回视图名
    }
}
