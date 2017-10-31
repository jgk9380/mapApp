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

public class ScanHandle implements WxMsgHandle {
    @Autowired
    WxInterfaceMessageDao wxInterfaceMessageDao;
    @Autowired
    WxPermQrCodeDao wxPermQrCodeDao;
    @Autowired
    WxManager wxManager;
    @Autowired
    WxUserDao wxUserDao;
    @Override
    public void handleEvent(WxInterfaceMessage wxInterfaceMessage) {
        String scaner_open_id=wxInterfaceMessage.getFromUserOpenId();
        String owner_id;
        JSONObject jsonObject=JSONObject.fromObject(wxInterfaceMessage.getContent());
        int scenen_id=jsonObject.getInt("EventKey");
        WxPermQrCode wxPermQrCode=wxPermQrCodeDao.findBySceneId(scenen_id);
        if(wxPermQrCode.getWxUserId()!=null){
            //订购前置页面preOrder.html：参数shopkeeper_open_id  直接跳转
            WxUser wxUser=wxUserDao.findById(wxPermQrCode.getWxUserId());
            String url="<a href='http://www.cu0515.con/preOrder.html?shopKeeper_open_id=" +wxUser.getOpenId()+ "'>订购业务</a>";
            wxManager.getWxOperator().sendTxtMessage(scaner_open_id,url);
            updateEvent(wxInterfaceMessage,"订购");
            return;

        }

        if(wxPermQrCode.getOwnerId()!=null&&wxPermQrCode.getWxUserId()==null){
            //发展代理页面agetSubmit.html：参数qrCode_id
            String url="<a href='http://www.cu0515.con/agetSubmit?qrCodeId=" +wxPermQrCode.getId()+ "'>代理发展</a>";
            wxManager.getWxOperator().sendTxtMessage(scaner_open_id,url);
            updateEvent(wxInterfaceMessage,"代理发展");
            return;
        }

        if(wxPermQrCode.getOwnerId()==null&&wxPermQrCode.getWxUserId()==null){
            //回复无效二维码
            wxManager.getWxOperator().sendTxtMessage(scaner_open_id,"无效二维码!!!");
            updateEvent(wxInterfaceMessage,"无效二维码");
            return;
        }

    }

    @Override
    public WxInterfaceMessageDao getWxInterfaceMessageDao() {
        return wxInterfaceMessageDao;
    }
}
