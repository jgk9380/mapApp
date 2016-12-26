package com.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jianggk on 2016/11/7.
 */
@Controller

public class PageController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login.action", method = RequestMethod.POST)
    @ResponseBody
    public String login(String username, String password) throws Exception {
        System.out.println("userName="+username+"  pwd="+password);
        return username + ":" + password;
    }
}
