package com.yzt.tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
 @WebMvcTest() // page 49: 从 @WebMvcTest 移除了对 HomeController 的引用
 // 针对HomeController的Web测试,
 // 测试在Spring MVC应用的上下文中执行(Spring Boot提供的特殊测试注解),
 // 为测试Spring Mvc应用提供Spring环境的支持
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // 注入MockMvc

    @Test
    public void testHomePage() throws Exception{
        /* 发起对”/“的GET */                   /* 期望得到HTTP 200 */      /* 期望得到home视图 */                            /* 期望包含”Welcome to...“ */
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home")).andExpect(content().string(containsString("Welcome to ...")));
    }
}
