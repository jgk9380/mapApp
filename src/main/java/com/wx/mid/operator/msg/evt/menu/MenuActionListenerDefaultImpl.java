package com.wx.mid.operator.msg.evt.menu;

import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.operator.msg.evt.EventActionListenerClickImpl;
import net.sf.json.JSONObject;


import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;


@Component("menuActionListenerDefaultImpl")
public class MenuActionListenerDefaultImpl implements MenuActionListener{
    public MenuActionListenerDefaultImpl() {
        super();
    }
    @Override
    public void doAction(WxUserMsg wum) {
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String toUserName = (String) json.get("FromUserName");
        String fromUserName = (String) json.get("ToUserName");
        String eventKey = json.getString("EventKey");
        wum.setHandleResult("û�д���");
        Logger.getLogger(EventActionListenerClickImpl.class).error("�˵���" + eventKey+"û�ж�Ӧ�Ĵ�����");
    }
}