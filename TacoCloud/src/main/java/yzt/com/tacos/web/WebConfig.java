package yzt.com.tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    // WebConfig 作为 WebMvcConfigurer 的实现

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        // 接收一个ViewControllerRegistry对象, 使用它注册视图控制器

        registry.addViewController("/").setViewName("home");
        /* 调用addViewController()方法, 将"/"传递进去, 视图控制器将会针对该路径执行GET请求,
        * 此方法返回一个ViewControllerRegistration对象,
        * 基于该对象调用setViewName()方法, 指明请求"/"时转发到"home"视图上
        * */
        registry.addViewController("/abc").setViewName("home");
        registry.addViewController("/login");
    }
}
