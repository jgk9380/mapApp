package com.wx.web;

import com.wx.mid.base.pojo.WeixinOauth2Token;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class WxMpAuth {
    @RequestMapping(value = "/MP_verify_LNKwjvrx0iNDE9om.txt", method = {RequestMethod.GET})
    @ResponseBody
    public String oauth(@PathVariable("code") String code) {
     return "LNKwjvrx0iNDE9om";
    }


//    MP_verify_LNKwjvrx0iNDE9om.txt
}
