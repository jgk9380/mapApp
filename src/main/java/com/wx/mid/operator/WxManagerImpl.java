package com.wx.mid.operator;


import com.wx.dao.WxAppDao;
import com.wx.dao.WxEventDao;
import com.wx.dao.WxUserDao;
import com.wx.entity.*;


//import com.wx.mid.util.WxUtils;
//import org.jboss.logging.Logger;
import com.wx.mid.handle.WxEventSpringEvent;


import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class WxManagerImpl implements WxManager, CommandLineRunner, InitializingBean, ApplicationEventPublisherAware {
    @Value("${wx.app.name}")
    String appName;

    @Autowired
    WxOperator wxOperator;
    @Autowired
    WxAppDao wxAppDao;
    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxUtils wxUtils;
    @Autowired
    WxEventDao wxEventDao;

    public WxManagerImpl() {

    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("--WxManagerImpl.run()  appId=" + this.appName);
        //todo 测试代码
        while(true) {
            List<WxEvent> l = wxEventDao.findByDispDateIsNull();
            l.stream().forEach(w -> this.applicationEventPublisher.publishEvent(new WxEventSpringEvent(w)));
            Thread.sleep(3000);
        }
    }

    @Override
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        return wxOperator.checkSignature(signature, timestamp, nonce);
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
    public WxOperator getWxOperator() {
        return this.wxOperator;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.printf("wxAppDao=" + wxAppDao + "\n");
        WxApp wx = wxAppDao.findByAppName(appName);
        if (wx == null) {
            Logger.getLogger(WxManagerImpl.class).error("找不到微信号：" + appName + "的配置数据");
            return;
        } else {
            Logger.getLogger(WxManagerImpl.class).info("微信号：" + appName + "的配置初始化成功");
        }
        wxOperator.initAppId(wx.getId());
    }


    public void addWxEvent(Map map, int flag) {
        JSONObject jsonObject=new JSONObject();
        for(Object key:map.keySet()){
            jsonObject.put(key,map.get(key));
        }
        WxEvent wxEvent = new WxEvent();
        wxEvent.setId(wxUtils.getSeqencesValue().intValue());
        wxEvent.setContent(jsonObject.toString());
        wxEvent.setOccureDate(new Date());
        wxEvent.setFlag(flag);
        wxEventDao.save(wxEvent);
        this.applicationEventPublisher.publishEvent(new WxEventSpringEvent(wxEvent));
        //发送Event
        return;
    }


    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
