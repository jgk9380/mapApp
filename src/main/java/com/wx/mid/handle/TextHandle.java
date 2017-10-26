package com.wx.mid.handle;

import com.wx.dao.WxInterfaceMessageDao;
import com.wx.dao.WxManualMessageDao;
import com.wx.entity.WxInterfaceMessage;
import com.wx.entity.WxManualMessage;
import com.wx.entity.WxUser;
import com.wx.mid.operator.WxManager;
import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TextHandle implements WxMsgHandle {
    @Autowired
    WxManager wxManager;
    @Autowired
    WxInterfaceMessageDao wxEventDao;
    @Autowired
    WxManualMessageDao wxManualMessageDao;
    @Autowired
    WxUtils wxUtils;
    @Override
    public void handleEvent(WxInterfaceMessage wxEvent) {
       // System.out.printf("TextEventHandle");  System.out.printf("SubscripeHandle");
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        String content=json.getString("Content");

       switch (content){
           case "号码绑定":
           case "hmbd":
               wxManager.getWxOperator().sendTxtMessage(wxEvent.getFromUserOpenId(),"回复："+content);
               updateEvent(wxEvent,"--号码绑定");
               break;
           case "我的海报":
           case "wdhb":
               wxManager.getWxOperator().sendTxtMessage(wxEvent.getFromUserOpenId(),"回复："+content);
               updateEvent(wxEvent,"--我的海报");
               break;
           case  "二维码":
           case "ewm":
               wxManager.getWxOperator().sendTxtMessage(wxEvent.getFromUserOpenId(),"回复："+content);
               updateEvent(wxEvent,"--二维码");
               break;
           case "我要代理":
           case "wydl":
               wxManager.getWxOperator().sendTxtMessage(wxEvent.getFromUserOpenId(),"回复："+content);
               updateEvent(wxEvent,"--我要代理");
               break;

           case "发展代理":
           case "fzdl":
               wxManager.getWxOperator().sendTxtMessage(wxEvent.getFromUserOpenId(),"回复："+content);
               updateEvent(wxEvent,"--发展代理");
               break;
           default:
               //TODO 进入客服信息库：
               System.out.println("处理客服信息");
               //wxManager.getWxOperator().sendTxtMessage(fromUserName,"回复："+"???");
               this.handleOtherTxtMessage(wxEvent);
               updateEvent(wxEvent,"--进入客服信息库，等待人工回复");
               break;
       }

    }

    @Override
    public WxInterfaceMessageDao getWxInterfaceMessageDao() {
        return wxEventDao;
    }

    private void handleOtherTxtMessage(WxInterfaceMessage wim) {
        JSONObject json = JSONObject.fromObject(wim.getContent());
        String fromUserName = json.getString("FromUserName");
        WxUser wxUser=wxManager.getWxUser(fromUserName);
        WxManualMessage wxManualMessage=new WxManualMessage();
        wxManualMessage.setId(wxUtils.getSeqencesValue().intValue());
        wxManualMessage.setWxUserId(wxUser.getId().intValue());
        wxManualMessage.setContent(json.getString("Content"));
        wxManualMessage.setReceivedDate(new Date());
        wxManualMessage.setReplyFlag(0);
        wxManualMessageDao.save(wxManualMessage);
    }

}
