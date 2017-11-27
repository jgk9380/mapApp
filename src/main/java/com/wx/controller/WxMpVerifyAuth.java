package com.wx.controller;

import com.wx.dao.WxUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller

public class WxMpVerifyAuth {
    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    TestTrans testTrans;

    @RequestMapping(value = "/MP_verify_LNKwjvrx0iNDE9om.txt", method = {RequestMethod.GET})
    @ResponseBody
    public String oauth(@PathVariable("code") String code) {
        return "LNKwjvrx0iNDE9om";
    }

    @RequestMapping(value = "/wx/transactionTest", method = {RequestMethod.GET})
    @ResponseBody
    public Date transactionTest() {
        testTrans.ddd();
        return new Date();
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public String  transactionTest11() {
      return  "";
   }
//    MP_verify_LNKwjvrx0iNDE9om.txt
}
