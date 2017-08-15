package com.control;

import com.dao.p.LoginUserDao;
import com.entity.p.Employee;
import com.entity.p.LoginUser;
import com.onesms.bean.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jianggk on 2017/1/23.
 */

//登录工号服务及员工信息服务
@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('MONTH_ADMIN')")
public class LoginUserAdminController {
    //@Secured("ROLE_ADMIN")
    //@Secured注释是用来定义业务方法的安全性配置属性列表。
    // 您可以使用@Secured在方法上指定安全性要求[角色/权限等]，只有对应角色/权限的用户才可以调用这些方法。
    // 如果有人试图调用一个方法，但是不拥有所需的角色/权限，那会将会拒绝访问将引发异常。
    @Autowired
    LoginUserDao lud;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value="/loginUser",method= RequestMethod.GET)
    List<LoginUser> getUsers() {
        return lud.findAll();
    }

    @RequestMapping(value="/loginUser/id",method= RequestMethod.GET)
    List<LoginUser> getUsers(String id) {
        return lud.findAll();
    }

    @RequestMapping(value="/loginUser",method={RequestMethod.POST,RequestMethod.PUT})
    LoginUser saveUser(@RequestBody LoginUser lu) {
        return lud.save(lu);
    }

    @RequestMapping(value="/loginUser/s",method={RequestMethod.POST,RequestMethod.PUT})
    LoginUser saveUsers(@RequestBody LoginUser[] lu) {
        return lud.save((LoginUser) Arrays.asList(lu));
    }

}
