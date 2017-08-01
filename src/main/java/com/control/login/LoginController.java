package com.control.login;


import com.config.security.JwtTokenUtil;
import com.config.security.JwtUser;
import com.dao.p.LoginUserDao;
import com.entity.p.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    private String tokenHeader = "Authorization";
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    LoginUserDao loginUserDao;
    //public static final String loginpwd = "$2a$10$qsYvMwvld7FMGKp45AQjpun6otC8b.eFN7Be5KAr0vuEQWgT.uvgm";

    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json;charset=utf8")
    public Map<String, String> login(@RequestParam(value = "username") String username, @RequestParam(value = "passwd") String passwd, Device device,HttpServletRequest req) {
        System.out.println("input name=" + username + "  pwd=" + passwd+ " device ="+device);
        req.getSession(true);
        LoginUser loginUser=loginUserDao.findByName(username);
//        if (!LoginController.loginname.equals(username)) {
//            System.out.println("----invalid userName----");
//            throw new BadCredentialsException("invalid userName1");
//        }

        if (loginUser==null) {
            throw new UsernameNotFoundException(String.format("登录用户错误: '%s'.", username));
        } else if(loginUser.getIsValid()==false) {
            throw new UsernameNotFoundException(String.format("用户 '%s'状态错误", username));
        }

        UsernamePasswordAuthenticationToken  upt=new UsernamePasswordAuthenticationToken(username,passwd);
        System.out.println(1);
        final Authentication authentication = authenticationManager.authenticate(upt);//此处调用UserDetailsService
        System.out.println(2);
        //用户名或密码错误的情况下什么情况？
        //System.out.println("--authenticationManager="+authenticationManager.toString());
        //System.out.println("--authentication="+authentication.toString());
        //System.out.println("authentication.name="+authentication.getName()  +"\n cred="+authentication.getCredentials().toString()                +"\n principal="+authentication.getPrincipal().toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);//此处设置安全信息
        System.out.println(3);
        // Reload password post-security so we can generate token
        //System.out.println("userDetailsService="+userDetailsService.toString());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);//此处生产token
        //System.out.println("userDetails="+userDetails.toString());
        // Perform the security
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        // Return the token
        // return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        HashMap<String, String> r = new HashMap<>();
        r.put("token", token);
        return r;
    }

    //获取当前登录用户名
    @RequestMapping(method = RequestMethod.GET,value = "/login")
    public Principal login(Principal principal) {
        return principal;
    }

    //获取真实用户名
    @RequestMapping(method = RequestMethod.GET,value = "/realLoginUser")
    public LoginUser getLoginUser(Principal principal) {
        return loginUserDao.findByName(principal.getName());
    }

    @RequestMapping(method = RequestMethod.POST,value = "/editPasswd")
    public boolean editPwd(@RequestParam("passwd")  String passwd,Principal principal) {
        LoginUser loginUser =loginUserDao.findByName(principal.getName());
        loginUser.setPassword(passwd);
        loginUserDao.save(loginUser);
        return true;
    }
}
