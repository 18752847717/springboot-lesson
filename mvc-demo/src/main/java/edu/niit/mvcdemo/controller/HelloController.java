package edu.niit.mvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WU, Homer
 * @version 1.0
 * @date 2020/3/24 8:41
 * @description
 */
@Controller
@RequestMapping("/")
public class HelloController {
    @GetMapping("/hello")
    public String hello(Model model){
        //使用resources/templates/hello.html作为模板
        //返回的字符串为hello.html的名称
        model.addAttribute("info","Hello,spring boot");
        return "hello";
    }
}
