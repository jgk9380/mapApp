package com.wx.web;

import com.onesms.WsTest;
import com.onesms.bean.SmsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/NoAuthService")
public class SmsServiceController {
    @Autowired
    private SmsService smsService;
    static Map<String, String> authCodeMap = new HashMap<>();
    static Map<String, Date> authCodeDateMap = new HashMap<>();

    @RequestMapping(path = "/smsAuth/{tele}")//发送验证码
    @ResponseBody
    public JSONObject getSmsAuth(HttpSession session, @PathVariable("tele") String tele) {

        Random random = new Random();
        if(authCodeDateMap.get(tele)!=null)
            if(((new Date().getTime())-authCodeDateMap.get(tele).getTime())<1000*60*2) {//2分钟后重发。
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("errorCode","1");
                jsonObject.put("errorMessage","二分钟前已发送");
                return jsonObject;
            }

        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
        }
        //将字符保存到session中用于前端的验证
        authCodeMap.put(tele, strCode);
        authCodeDateMap.put(tele, new Date());
        String smsContent="尊敬的用户:您本次验证码为"+strCode+",请在10分钟内使用。";
        String res=smsService.sendSms(tele, smsContent);
        System.out.println("sms res = [" + res + "]");
        if (res.contains("result=0")){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("errorCode","0");
            jsonObject.put("errorMessage","已发送");
        }
        else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("errorCode","1");
            jsonObject.put("errorMessage",res);
        }
            return null;
    }

    @RequestMapping(path = "/smsAuth/{tele}/{code}")
    @ResponseBody
    public boolean authSmsAuth(HttpSession session, @PathVariable("tele") String tele, @PathVariable("code") String code) {
        //        Random random = new Random();
        //        String strCode = "";
        //        for (int i = 0; i < 4; i++) {
        //            String rand = String.valueOf(random.nextInt(10));
        //            strCode = strCode + rand;
        //        }
        //        //将字符保存到session中用于前端的验证
        //        authCodeMap.put(tele,strCode);
        //        smsService.sendSms(tele, strCode);
        System.out.println("map.size=" + authCodeMap.size());
        System.out.println("code1=" + authCodeMap.get(tele)+" tele0"+tele+"code0="+code);
        shrinkMap();
        return true;
    }

    //todo 调整map大小 map>1000时删除30分钟前的数据。
    public void shrinkMap() {
        if (authCodeMap.size() > 500) {
            System.out.println("\n--------shrink smsMap---------\n");//todo logit
            for (String dataTele : authCodeDateMap.keySet()) {
                if ((new Date().getTime()) - authCodeDateMap.get(dataTele).getTime() > 1000 * 60 * 30) {
                    authCodeDateMap.remove(dataTele);
                    authCodeMap.remove(dataTele);
                }
            }
        }
    }
}
