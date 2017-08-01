package com.control.old;
//

import com.dao.s.ChargeCardDao;
import com.entity.s.ChangeCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

///**
// * Created by jianggk on 2016/10/20.
// */
@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    ChargeCardDao ccd;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/dd")
    public ChangeCard getCc() {
        ChangeCard cc = ccd.findByDeviceNumber("15651554341");
        return cc;
    }

    @RequestMapping("/user")
    Principal principal(Principal principal) {
        return principal;
    }

}