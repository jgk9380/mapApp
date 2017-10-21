package com.wx.mid.handle;

import com.wx.entity.WxEvent;
import com.wx.mid.operator.WxManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscripeHandle  implements EventHandle{

    @Autowired
    WxManager wxManager;
    @Override
    public void handleEvent(WxEvent wxEvent) {
        System.out.printf("SubscripeHandle");
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        String fromUserName = json.getString("FromUserName");
        wxManager.getWxOperator().sendTxtMessage(fromUserName,"您好，欢迎盐城通信圈");
        wxEvent.setDispResult("--回复用户欢迎光临");
    }
}
