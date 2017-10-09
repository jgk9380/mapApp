package com.wx.mid.operator;

import java.util.Date;


import com.wx.mid.base.pojo.WeixinUserInfo;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.dao.WxUserDao;
import com.wx.mid.entity.*;



//import com.wx.mid.util.WxUtils;
//import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

//@Component
public class WxManagerImpl implements WxManager {

    String appName;

    WxOperator wxOperator;
    //@Autowired
    WxAppDao wxAppDao;
    //@Autowired
    WxUserDao wxUserDao;

    public WxManagerImpl() {
        this.appName = "yctxq";
    //    WxApp wx = wxAppDao.findByAppName("wx7dcc6b2e03a47c0b");
//        if (wx == null) {
//            Logger.getLogger(WxManagerImpl.class).error("找不到微信号：" + appName);
//            return;
//        }
        wxOperator.initAppId("wx7dcc6b2e03a47c0b");
    }

    @Override
    public WxUser getWxUser(String openId) {
//        WxUser res;
//        //System.out.println("--appName=" + appName);
//        WxApp wxApp = wxAppDao.findByAppName(appName);
//        //Logger.getLogger(WxAppManagerImpl.class).info(operator);
//        //System.out.println("wxappId=" + operator.getId() + "   openId " + openId);
//        res = wxUserDao.findByAppIdAndOpenId(wxApp.getId(), openId);
//        //res = wxUserDao.findByOpenId( openId);
//        Date currentDate = new Date();
//        if (res != null && res.getNickname() != null && res.getRefereshDate() != null) { //����Ϊ�ջ��ǳ�Ϊ�ջ��ϴθ���ʱ�䳬��һ��
//            //System.out.println("res=" + res + "id=" + res.getId() + "nickName=" + res.getNickname());
//            if ((currentDate.getTime() - res.getRefereshDate().getTime()) / 1000 / 3600 / 24 > 7) //����ʱ�䳬��һ����
//                res = refreshWxUser(openId);
//            return res;
//        }
//        res = refreshWxUser(openId);
//        return res;
        return null;
    }

    @Override
    public WxOperator getOperator() {
        return wxOperator;
    }

    public WxUser refreshWxUser(String openId) {
//        WxUser res;
//        WxApp wx = wxAppDao.findByAppName(appName);
//        res = wxUserDao.findByAppIdAndOpenId(wx.getId(), openId);
//        //res = wxUserDao.findByOpenId(openId);
//        if (res == null) {
//            res = new WxUser();
//            res.setId(WxUtils.getSeqencesValue().longValue());
//            res.setWxApp(wx);
//            res.setOpenId(openId);
//        }
//        WeixinUserInfo wui = wxOperator.getUserInfo(openId);
//        if (wui == null) {
//            Logger logger = Logger.getLogger(WxManagerImpl.class);
//            logger.error("�����openId��" + openId);
//            return null;
//        } else if (wui.getSubscribe() == 0) {
//            if (res.getSubscribeStatus() == 1)
//                res.setSubscribeStatus(-1);
//            return wxUserDao.save(res);
//        } else {
//            res.setNickname(wui.getNickname());
//            res.setSex("" + wui.getSex());
//            res.setCountry(wui.getCountry());
//            res.setCity(wui.getCity());
//            res.setLanguage(wui.getLanguage());
//            res.setHeadimgurl(wui.getHeadImgUrl());
//            //System.out.println("ss=" + wui.getSubscribe());
//            res.setSubscribeStatus(wui.getSubscribe());
//            Long subTime = Long.parseLong(wui.getSubscribeTime()) * 1000;
//            Date subDate = new Date(subTime);
//            res.setSubscribeDate(subDate);
//            res.setProvince(wui.getProvince());
//            res.setRefereshDate(new Date());
//            //res.setUserGroup(userGroup);�����û�����
//            return wxUserDao.save(res);
//        }
        return null;
  }

    public String getAppName() {
        return appName;
    }
}
