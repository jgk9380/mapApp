package com.wx.mid;


import com.wx.mid.entity.WxUser;
import com.wx.mid.util.UserService;
import com.wx.mid.util.WxPictTool;
import com.wx.mid.operator.PromotionGiftSelector;
import com.wx.mid.operator.PromotionManager;
import com.wx.mid.operator.WxAppManager;

import com.wx.mid.operator.bo.Account;
import com.wx.mid.operator.bo.WxUserBo;
import com.wx.mid.operator.data.service.DataServiceListener;
import com.wx.mid.operator.msg.MsgActionListener;
import com.wx.mid.operator.msg.evt.EventActionListener;
import com.wx.mid.operator.msg.evt.menu.MenuActionListener;
import org.springframework.jdbc.core.JdbcTemplate;



public interface WxBeanFactory {
    /**����һ���������ö��΢�ź�*/
       //  WxAppManager getWxAppManagerByAppId(String appId);
       //WxAppManager getWxAppManagerByAppUserName(String appUserName);
       //WxAppManager getWxAppManagerByAppName(String fromUser);
    WxAppManager getWxAppManager(String appName);
    MsgActionListener getMsgActionListener(String name);
    EventActionListener getEvtActionListener(String name);
    MenuActionListener getMenuActionListener(String name);
    DataServiceListener getDataServiceListener(String name);
    UserService getUserService();
    JdbcTemplate getJdbcTemplate();
    PromotionGiftSelector getPromotionGiftSelector(String name);

    WxConfig getConfig();    
    PromotionManager getPromotionManager() ;
    <T> T getBean(String beanName, Class<T> clazz);   
    WxPictTool getWxPictTool();

    WxUserBo getUserBo(WxUser wxUser);

    Account getAccount(int i);
}
