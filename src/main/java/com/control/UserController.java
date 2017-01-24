package com.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by jianggk on 2017/1/23.
 */
@RestController
public class UserController {
    @RequestMapping("/user")
    Principal principal(Principal principal) {
        return principal;
    }
}
