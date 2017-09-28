package com.wx.mid.operator.msg.evt.menu;

import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.operator.WxAppManager;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//业务查询
@Component("menuActionListenerYWCXImpl")
public class MenuActionListenerYWCXImpl implements MenuActionListener{
    public MenuActionListenerYWCXImpl() {
        super();
    }
    @Autowired
    WxAppDao wxAppDao;
    @Override
    public void doAction(WxUserMsg wum) {
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String toUserName = (String) json.get("ToUserName");
        String fromUserName = (String) json.get("FromUserName");
        String eventKey = json.getString("EventKey");        
        WxAppManager wam =
            WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
    
        String content = "欢迎下载手机营业厅进行话费,流量,套餐查询。\n下载地址: http://wap.10010.com/knd ";
        boolean res = wam.getOperator().sendTxtMessage(fromUserName, content);
        wum.setHandleResult("回复：" + content);
       
    
    }
}
