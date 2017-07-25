package com.control;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestJwtController {
    @ResponseBody
    //@PreAuthorize("hasRole('xtgl')")
    @RequestMapping("/testjwt1")
    public Map testJwt() {
        Map map = new HashMap<>();
        map.put("data", "abc");
        return map;
    }

    @ResponseBody
    @PreAuthorize("hasRole('xtgll')")
    @RequestMapping("/testjwt2")
    public Map testJwt2() {
        Map map = new HashMap<>();
        map.put("data", "abc2");
        return map;
    }

}
