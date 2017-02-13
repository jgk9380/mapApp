package com.control;

import com.dao.p.LoginUserDao;
import com.entity.p.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by jianggk on 2017/1/23.
 */

@RestController
@RequestMapping("/users")
public class LoginUserController {
    //@Secured("ROLE_ADMIN")
    //@Secured注释是用来定义业务方法的安全性配置属性列表。
    // 您可以使用@Secured在方法上指定安全性要求[角色/权限等]，只有对应角色/权限的用户才可以调用这些方法。
    // 如果有人试图调用一个方法，但是不拥有所需的角色/权限，那会将会拒绝访问将引发异常。

    @Autowired
    LoginUserDao lud;

    @RequestMapping(value="/",method= RequestMethod.GET)
    List<LoginUser> getUsers(String id) {
        return lud.findAll();
    }

    @RequestMapping(value="/{name}",method= RequestMethod.GET)
    LoginUser getUser(@PathVariable("name") String name) {
        return lud.findByName(name);
    }

    @RequestMapping(value="/editPwd/{pwd}",method= RequestMethod.POST)
    ReponseResult editPwd(@PathVariable("pwd") String pwd,Principal principal) {
        LoginUser lu= lud.findByName(principal.getName());
        lu.setPassword(pwd);
        lud.save(lu);
        return new ReponseResult(1,"ok");
    }

    //用户客户端登录测试及取得当前用户。
    @RequestMapping("/currentUser")
    Principal principal(Principal principal) {
        return principal;
    }

}
