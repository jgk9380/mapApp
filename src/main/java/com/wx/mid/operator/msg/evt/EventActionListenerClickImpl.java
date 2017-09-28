package com.wx.mid.operator.msg.evt;

import java.io.PrintWriter;

import java.util.Date;


import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.entity.menu.WxClickMenu;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.*;
import com.wx.mid.operator.WxAppManager;
import com.wx.mid.operator.msg.evt.menu.MenuActionListener;
import net.sf.json.JSONObject;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("clickEvtActionListenBaseImpl")

public class EventActionListenerClickImpl implements EventActionListener {
    public EventActionListenerClickImpl() {
        super();
    }
    @Autowired
    WxUserMsgDao wxUserMsgDao;
    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxAppDao wxAppDao;
    @Autowired
    WxPerQrCodeDao wxPerQrCodeDao;
    @Autowired
    WxMenuDao wxMenuDao;

    //   @Override
    public void doAction(WxUserMsg wum, PrintWriter out) {
      
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String toUserName = (String) json.get("FromUserName");
        String fromUserName = (String) json.get("ToUserName");
        String eventKey = json.getString("EventKey");
        WxAppManager wam = WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        WxClickMenu wxMenu = wxMenuDao.findByKey(wum.getWxUser().getWxApp().getAppName(),eventKey);
        String handleClassName = null;
        if (wxMenu != null)
            handleClassName = wxMenu.getHandelClassName();
        MenuActionListener mal = WxBeanFactoryImpl.getInstance().getMenuActionListener(handleClassName);
        mal.doAction(wum);
        wum.setHandleTime(new Date());
        wxUserMsgDao.save(wum);
    };

    @Override
    public void doAction(WxUserMsg wum) {
        
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String toUserName = (String) json.get("FromUserName");
        String fromUserName = (String) json.get("ToUserName");
        String eventKey = json.getString("EventKey");
        WxAppManager wam = WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        WxClickMenu wxMenu = wxMenuDao.findByKey(wum.getWxUser().getWxApp().getAppName(),eventKey);
        String handleClassName = null;
        if (wxMenu != null)
            handleClassName = wxMenu.getHandelClassName();
        
        MenuActionListener mal = WxBeanFactoryImpl.getInstance().getMenuActionListener(handleClassName);
        mal.doAction(wum);
        wum.setHandleTime(new Date());
        wxUserMsgDao.save(wum);
    }
}
