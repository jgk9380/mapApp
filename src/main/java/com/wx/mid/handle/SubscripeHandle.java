package com.wx.mid.handle;

import com.wx.dao.WxInterfaceMessageDao;
import com.wx.entity.WxInterfaceMessage;
import com.wx.mid.operator.WxManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscripeHandle  implements WxMsgHandle {
    @Autowired
    WxManager wxManager;
    @Autowired
    WxInterfaceMessageDao wxEventDao;
    @Override
    public void handleEvent(WxInterfaceMessage wxEvent) {
        System.out.printf("SubscripeHandle");
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        String fromUserName = json.getString("FromUserName");
        System.out.printf("---wxUser.name="+wxManager.getWxUser(fromUserName).getNickname());
        wxManager.getWxOperator().sendTxtMessage(fromUserName,"您好，欢迎盐城通信圈");
        updateEvent(wxEventDao,wxEvent,"--回复用户欢迎光临");
    }
}
