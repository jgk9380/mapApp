package com.wx.web;

import com.wx.dao.WxUserDao;
import com.wx.entity.WxUser;
import com.wx.mid.base.pojo.WeixinOauth2Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
//    MP_verify_LNKwjvrx0iNDE9om.txt
}
