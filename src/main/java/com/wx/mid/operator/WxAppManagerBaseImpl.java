package com.wx.mid.operator;


import java.util.Date;


import com.wx.mid.base.pojo.WeixinUserInfo;
import com.wx.mid.entity.WxApp;
import com.wx.mid.entity.WxUser;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.dao.WxUserDao;
import com.wx.mid.util.UserService;
import com.wx.mid.util.WxUtils;
import org.jboss.logging.Logger;


public abstract class WxAppManagerBaseImpl implements WxAppManager {
    String appName;
    UserService userService = WxBeanFactoryImpl.getInstance().getBean("userService", UserService.class);
    WxAppDao wxAppDao = WxBeanFactoryImpl.getInstance().getBean("wxAppDao", WxAppDao.class);
    WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
    WxOperator wxOperator;

    public WxAppManagerBaseImpl(String appName) {
        this.appName = appName;
        WxApp wx = wxAppDao.findByAppName(appName);
        if (wx == null) {
            Logger.getLogger(WxAppManagerBaseImpl.class).error("û��AppId��" + appName);
            return;
        }
        wxOperator = new WxOperatorImpl(wx.getId());
    }


    public WxUser refreshWxUser(String openId) {
        WxUser res;
        WxApp wx = wxAppDao.findByAppName(appName);
        res = wxUserDao.findByAppIdAndOpenId(wx.getId(), openId);
        //res = wxUserDao.findByOpenId(openId);
        if (res == null) {
            res = new WxUser();
            res.setId(WxUtils.getSeqencesValue().longValue());
            res.setWxApp(wx);
            res.setOpenId(openId);
        }
       
        WeixinUserInfo wui = wxOperator.getUserInfo(openId);

        if (wui == null) {
            Logger logger = Logger.getLogger(WxAppManagerImpl.class);
            logger.error("�����openId��" + openId);
            return null;
        } else if (wui.getSubscribe() == 0) {
            if (res.getSubscribeStatus() == 1)
                res.setSubscribeStatus(-1);
            return wxUserDao.save(res);
        } else {             
            res.setNickname(wui.getNickname());            
            res.setSex("" + wui.getSex());
            res.setCountry(wui.getCountry());
            res.setCity(wui.getCity());
            res.setLanguage(wui.getLanguage());
            res.setHeadimgurl(wui.getHeadImgUrl());
            //System.out.println("ss=" + wui.getSubscribe());
            res.setSubscribeStatus(wui.getSubscribe());

            Long subTime = Long.parseLong(wui.getSubscribeTime()) * 1000;
            Date subDate = new Date(subTime);
            res.setSubscribeDate(subDate);
            res.setProvince(wui.getProvince());
            res.setRefereshDate(new Date());
            //res.setUserGroup(userGroup);�����û�����
            return wxUserDao.save(res);
        }

    }

    public String getAppName() {
        return appName;
    }


    public static void main(String[] args) {
        ;
    }

}
