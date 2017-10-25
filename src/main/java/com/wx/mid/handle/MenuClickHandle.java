package com.wx.mid.handle;

import com.wx.dao.WxInterfaceMessageDao;
import com.wx.entity.WxInterfaceMessage;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuClickHandle implements WxMsgHandle {
    @Autowired
    WxInterfaceMessageDao wxEventDao;
    @Override
    public void handleEvent(WxInterfaceMessage wxEvent) {
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        String eventKey = json.getString("EventKey");
        System.out.println("/n------ clicked eventKey="+eventKey);
        updateEvent(wxEventDao,wxEvent,"菜单clicked 已处理");
    }
}
