package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainControl {
    @RequestMapping("/")
    public String main(){
        return"/index";
    }
    @RequestMapping("/index.html")
    public String index(){
        return"/index";
    }
}
