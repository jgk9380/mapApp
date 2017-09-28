package com.wx.mid;


import java.util.HashMap;

import java.util.Map;


import com.wx.mid.entity.WxUser;
import com.wx.mid.util.UserService;
import com.wx.mid.util.WxPictTool;
import com.wx.mid.operator.PromotionGiftSelector;
import com.wx.mid.operator.PromotionManager;
import com.wx.mid.operator.WxAppManager;
import com.wx.mid.operator.WxAppManagerImpl;
import com.wx.mid.operator.bo.Account;
import com.wx.mid.operator.bo.WxUserBo;
import com.wx.mid.operator.data.service.DataServiceListener;
import com.wx.mid.operator.msg.MsgActionListener;
import com.wx.mid.operator.msg.evt.EventActionListener;
import com.wx.mid.operator.msg.evt.menu.MenuActionListener;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


public class WxBeanFactoryImpl implements WxBeanFactory {
    Map<String, WxAppManager> wxAppManagerMap = new HashMap<>();
    static WxBeanFactory instance;
    ApplicationContext ctx;

    private WxBeanFactoryImpl() {
        ctx = new ClassPathXmlApplicationContext("beans.xml");
    }

    public static WxBeanFactory getInstance() {
        if (instance == null)
            instance = new WxBeanFactoryImpl();
        return instance;
    }

    @Override
    public WxAppManager getWxAppManager(String appName) {
        System.out.println("--appName="+appName);
        if (wxAppManagerMap.containsKey(appName))
            return wxAppManagerMap.get(appName);

        WxAppManagerImpl wf = new WxAppManagerImpl(appName);
        wxAppManagerMap.put(appName, wf);
        return wf;
    }

    @Override
    public MsgActionListener getMsgActionListener(String name) {
        if (name == null)
            name = "defaultMsgActionListener";
        MsgActionListener wf = (MsgActionListener) ctx.getBean(name);
        //TODO ifnull
        return wf;
    }


    @Override
    public EventActionListener getEvtActionListener(String name) {
        if (name == null)
            name = "defaultEvtActionListenBaseImpl";
        EventActionListener wf = (EventActionListener) ctx.getBean(name);
        //TODO ifnull
        return wf;
    }

    public static void main(String[] args) {
        WxBeanFactoryImpl wfi = new WxBeanFactoryImpl();
        Object o = wfi.getPromotionGiftSelector(null);
        //System.out.println("o=" + o);

    }
    @Override
    public PromotionManager getPromotionManager() {
        PromotionManager pm = (PromotionManager) ctx.getBean("promotionManager");
        return pm;
    }




    @Override
    public DataServiceListener getDataServiceListener(String key) {
        String name = key;
//        if (!this.getConfig().getTargetConifig().containsKey(key))
//            name = key;
//        else
//            name = this.getConfig().getTargetConifig().get(key);

        return (DataServiceListener) ctx.getBean(name);
    }

  


    @Override
    public UserService getUserService() {
        UserService us = (UserService) ctx.getBean("userService");
        return us;
    }


    @Override
    public <T> T getBean(String beanName, Class<T> clazz) {
        return ctx.getBean(beanName, clazz);
    }

    @Override
    public WxConfig getConfig() {
        return ctx.getBean("wxConfig", WxConfig.class);
    }


    @Override
    public JdbcTemplate getJdbcTemplate() {
        return ctx.getBean("jdbcTemplate", JdbcTemplate.class);
    }

    @Override
    public WxPictTool getWxPictTool() {
         return ctx.getBean("wxPictTool", WxPictTool.class);
    }

    @Override
    public WxUserBo getUserBo(WxUser wxUser) {
        return null;
    }

    @Override
    public Account getAccount(int i) {
        return null;
    }


    @Override
    public PromotionGiftSelector getPromotionGiftSelector(String name) {
        if (name == null)
            name = "defaultPromotionGiftSelector";
        //System.out.println("selectName=" + name);
        PromotionGiftSelector seletor = ctx.getBean(name, PromotionGiftSelector.class);
        return seletor;
    }

    @Override
    public MenuActionListener getMenuActionListener(String name) {
        if (name == null)
            name = "menuActionListenerDefaultImpl";
        return ctx.getBean(name, MenuActionListener.class);
    }


  
}
