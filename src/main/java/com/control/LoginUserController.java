package com.control;

import com.dao.p.LoginUserDao;
import com.entity.p.Employee;
import com.entity.p.LoginUser;
import com.onesms.bean.SmsService;
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

//登录工号服务及员工信息服务
@RestController
@RequestMapping("/users")
public class LoginUserController {
    //@Secured("ROLE_ADMIN")
    //@Secured注释是用来定义业务方法的安全性配置属性列表。
    // 您可以使用@Secured在方法上指定安全性要求[角色/权限等]，只有对应角色/权限的用户才可以调用这些方法。
    // 如果有人试图调用一个方法，但是不拥有所需的角色/权限，那会将会拒绝访问将引发异常。

    @Autowired
    LoginUserDao lud;
    @Autowired
    private SmsService smsService;

    @RequestMapping(value="/",method= RequestMethod.GET)
    List<LoginUser> getUsers(String id) {
        return lud.findAll();
    }

    @RequestMapping(value="/{name}",method= RequestMethod.GET)
    LoginUser getUser(@PathVariable("name") String name) {
        return lud.findByName(name);
    }

    @RequestMapping(value="/editPwd/{userId}/{pwd}",method= RequestMethod.POST)
    ReponseResult editPwd(@PathVariable("pwd") String pwd,@PathVariable("userId") String userId) {
        LoginUser lu= lud.findByName(userId);
        if(lu==null)
            return new ReponseResult(-1,"没有有效用户");
        lu.setPassword(pwd);
        lud.save(lu);
        System.out.println("密码修改成功");
        return new ReponseResult(1,"密码修改成功");
    }

    //用户客户端登录测试及取得当前用户。
    @RequestMapping("/currentUser")
    Principal principal(Principal principal) {
        return principal;
    }

    @RequestMapping("/queryPwd/{userId}")
    ReponseResult queryPwd(@PathVariable("userId") String userId) {
        LoginUser lu= lud.findByName(userId);
        if(lu==null){
            return new ReponseResult(-1,"没有该用户");
        }
        if(!lu.getIsValid()){
            return new ReponseResult(-1,"失效用户");
        }
        if(lu.getEmployee()==null||lu.getEmployee().getTele()==null){
            return new ReponseResult(-1,"没有用户emp数据或没有电话号码数据");
        }
        //TODO发送短信代码
        //smsService.sendSms(lu.getEmployee().getTele(),"你的密码为：["+lu.getPassword()+"],请妥善保管");
        System.out.println("发送短信 号码："+lu.getEmployee().getTele()+"     发送内容：\"你的密码为：["+lu.getPassword()+"],请妥善保管\"");
        return new ReponseResult(1,"密码已发送到尾号为"+lu.getEmployee().getTele().substring(7)+"的号码");
    }

    @RequestMapping("/valid/{userId}/{pwd}")//验证用户信息
    ReponseResult valid(@PathVariable("userId") String userId,@PathVariable("pwd") String pwd) {
        if(userId==null||pwd==null)
            return new ReponseResult(1,"没有用户名或密码");
        LoginUser lu= lud.findByName(userId);
        if(lu==null){
            return new ReponseResult(1,"没有该用户");
        }
        if(!lu.getIsValid()){
            return new ReponseResult(1,"失效用户");
        }
        if(!lu.getPassword().equals(pwd)){
            return new ReponseResult(1,"密码错，输入的密码为："+pwd);
        }
        return new ReponseResult(1,"登录信息正确");
    }

}
