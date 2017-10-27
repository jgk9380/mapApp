package com.wx.mid.handle;

import com.wx.dao.WxInterfaceMessageDao;
import com.wx.dao.WxPermQrCodeDao;
import com.wx.dao.WxUserDao;
import com.wx.entity.WxInterfaceMessage;
import com.wx.entity.WxPermQrCode;
import com.wx.entity.WxUser;
import com.wx.mid.operator.WxManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscripeHandle  implements WxMsgHandle {

    //EventKey	事件KEY值，qrscene_为前缀，后面为二维码的参数值
    //测试 https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEC8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0hUcUFKT3psSEdGbC10NkRiaGJXAAIEVYyoVgMEAAAAAA==
    @Autowired
    WxManager wxManager;
    @Autowired
    WxInterfaceMessageDao wxEventDao;
    @Autowired
    WxPermQrCodeDao wxPermQrCodeDao;
    @Autowired
    WxUserDao wxUserDao;
    @Override
    public void handleEvent(WxInterfaceMessage wxEvent){
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        String fromUserName = json.getString("FromUserName");
        //System.out.printf("---wxUser.name="+wxManager.getWxUser(fromUserName).getNickname());
        wxManager.getWxOperator().sendTxtMessage(fromUserName,"您好，欢迎盐城通信圈");
        if(json.containsKey("EventKey")){
            WxUser wxUser=wxManager.getWxUser(fromUserName);
            String eventKey=json.getString("EventKey");
            String sceneId=eventKey.substring(8);
           // System.out.println("scene_id = [" + sceneId + "]");
            WxPermQrCode wxPermQrCode =wxPermQrCodeDao.findBySceneId(Integer.parseInt(sceneId));
            wxUser.setReferee(wxUserDao.findById(wxPermQrCode.getWxUserId()));
            wxUserDao.save(wxUser);
        }
        updateEvent(wxEvent,"--回复用户欢迎光临");
    }

    @Override
    public WxInterfaceMessageDao getWxInterfaceMessageDao() {
        return wxEventDao;
    }
}
