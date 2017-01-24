package com.control;

import com.bea.httppubsub.json.JSONObject;
import com.dao.p.LoginUserDao;
import com.entity.p.LoginUsers;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
public class LoginController {
    @Autowired
    LoginUserDao lud;

    @RequestMapping("/login/{name}/{pwd}")
    // @CrossOrigin(origins = "**", maxAge = 3600)
    public ReponseResult login(@PathVariable("name") String name, @PathVariable("pwd") String pwd) {
        //res.addHeader("Access-Control-Allow-Origin","*");
        System.out.println("name="+name+"   pwd="+pwd);
        ReponseResult rr = new ReponseResult();
        LoginUsers lu = lud.findByName(name);
        if (lu == null) {
            rr.setResult(-1);
            rr.setMsg("错误的用户名");
            return rr;
        }
        if (lu.getIsValid() != 1) {
            rr.setResult(-1);
            rr.setMsg("错误的用户状态");
            return rr;
        }
        if (!lu.getPassword().equals(pwd)) {
            System.out.println("pwd="+lu.getPassword());
            rr.setResult(-1);
            rr.setMsg("错误的用户密码");
            return rr;
        }

        rr.setResult(1);
        rr.setMsg("登录成功");

        return rr;
        //return "index.html";

    }

}
