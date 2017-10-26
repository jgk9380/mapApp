package com.wx.web;

import com.wx.mid.base.util.MessageUtil;
import com.wx.mid.operator.WxManager;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;


import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/wx")
public class WxServiceController {
    public WxServiceController() {
        super();
    }
    @Autowired
    WxManager wxManager;
    @RequestMapping(value = "/core", method = {RequestMethod.GET})
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger(WxServiceController.class).info("doGet");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        Logger.getLogger(WxServiceController.class).info("nonce=" + nonce + "signature=" + signature + "   timestamp=" + timestamp + "   echostr=" + echostr);
        PrintWriter out = response.getWriter();
        boolean result= wxManager.checkSignature(signature, timestamp, nonce);
        String info="校验：nonce=" + nonce + "signature=" + signature + "   timestamp=" + timestamp + "   echostr=" + echostr  +"结果："+result;
       //wxManager.addInterfaceMsg(info,-1);
        if (result) {
            Logger.getLogger(WxServiceController.class).warn("\n---success");
            out.print(echostr);
        } else {
            Logger.getLogger(WxServiceController.class).warn("\n----no success,and passed it ");
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    @ResponseBody
    public String test() {
        return "test";
    }

    /**
     * 处理微信服务器发来的消息
     */
    @RequestMapping(value = "/core", method = {RequestMethod.POST})
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //发送留言
        try {
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            System.out.println("\n---接收到：" + requestMap);
            wxManager.addInterfaceMsg(requestMap);
            PrintWriter out = response.getWriter(); //可以回复空串
            out.println("success");//todo 保存后待处理
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
