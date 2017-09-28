package com.wx.mid.operator.msg.evt.menu;

import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.util.UnicomRobot;
import com.wx.mid.operator.WxAppManager;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//����һЦ
@Component("menuActionListenerKXYXImpl")
public class MenuActionListenerKXYXImpl implements MenuActionListener {
    public MenuActionListenerKXYXImpl() {
        super();
    }
    @Autowired
    WxAppDao wxAppDao;

    @Override
    public void doAction(WxUserMsg wum) {
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String toUserName = (String) json.get("FromUserName");
        String fromUserName = (String) json.get("ToUserName");
        String eventKey = json.getString("EventKey");
        WxAppManager wam =
            WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        String content = UnicomRobot.getAnswer("讲个笑话", wum.getWxUser().getId().toString());
        boolean res = wam.getOperator().sendTxtMessage(toUserName, content);
        wum.setHandleResult("回复：" + content);
    }
}
