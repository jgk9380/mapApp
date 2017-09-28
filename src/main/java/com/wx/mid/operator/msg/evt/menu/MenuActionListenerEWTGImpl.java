package com.wx.mid.operator.msg.evt.menu;

import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.operator.WxAppManager;
import com.wx.mid.operator.bo.WxUserBo;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//二维推广
@Component("menuActionListenerEWTGImpl")
public class MenuActionListenerEWTGImpl implements MenuActionListener{
    public MenuActionListenerEWTGImpl() {
        super();
    }
    @Autowired
    WxAppDao wxAppDao;
    @Override
    public void doAction(WxUserMsg wum) {
      
       //mid.operator.WxAppManagerImpl.dispUserMsg//(WxAppManagerImpl.java:155)
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String sender = (String) json.get("FromUserName");
        String fromUserName = (String) json.get("ToUserName");
        String eventKey = json.getString("EventKey");
        WxAppManager wam =
            WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        
        WxUserBo wuBo = wam.getWxUserBo(sender);
        String mediaId = wuBo.getShareImageMediaId();
        wam.getOperator().sendTxtMessage(sender,
                                                "将下面的图片分享至朋友圈或发送至你的好友，邀请你的朋友一起参加\"盐城联通\"新春送礼活动!邀请越多获奖越大，可得到IPhone，品牌智能手机等礼品!");
        wam.getOperator().sendImageMessage(sender, mediaId);
        
    }
}
