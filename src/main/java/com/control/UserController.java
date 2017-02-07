package com.control;

import com.entity.p.LoginUsers;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by jianggk on 2017/1/23.
 */

@RestController
@RequestMapping("/users")
public class UserController {
    //@Secured("ROLE_ADMIN")
    //@Secured注释是用来定义业务方法的安全性配置属性列表。
    // 您可以使用@Secured在方法上指定安全性要求[角色/权限等]，只有对应角色/权限的用户才可以调用这些方法。
    // 如果有人试图调用一个方法，但是不拥有所需的角色/权限，那会将会拒绝访问将引发异常。
    @RequestMapping("/currentUser")
    Principal principal(Principal principal) {
        return principal;
    }
    @RequestMapping("/{id}")
    LoginUsers getCurrentUser(@PathVariable("id") String id){
        return null;
    }
    @RequestMapping("/")
    List<LoginUsers> getUsers(String id){
        return null;
    }
}
