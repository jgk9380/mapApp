package com.wx.mid.operator.msg;

import java.io.PrintWriter;

import java.util.Date;

import com.wx.mid.base.message.req.TextMessage;
import com.wx.mid.base.util.MessageUtil;
import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxUserMsgDao;
import com.wx.mid.util.UnicomRobot;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("textMsgActionListener")
//TODO �ĳ�ʹ�ÿͷ��ӿ�
public class MsgActionListenerTextImpl implements MsgActionListener  {
    @Autowired
    WxUserMsgDao wxUserMsgDao;
    
    public String doAction(WxUserMsg wum, PrintWriter out) {
        
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String content = (String) json.get("Content");
      
        String result = null;
        String  toUserName  = (String) json.get("FromUserName");
        // ������΢�ź�
        String fromUserName = (String) json.get("ToUserName");
        try {
            result = UnicomRobot.getAnswer(content, wum.getWxUser().getId().toString());
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(toUserName);
            textMessage.setFromUserName(fromUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setContent(result);
            out.println(MessageUtil.messageToXml(textMessage));
            wum.setHandleTime(new Date());
            wum.setHandleResult("�ظ��û���"+result);
            wxUserMsgDao.save(wum);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO log;
        }        
        return result;
    }

    @Override
    public String doAction(WxUserMsg wum) {
      
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String content = (String) json.get("Content");
       
        String result = null;
        String  toUserName  = (String) json.get("FromUserName");
        // ������΢�ź�
        String fromUserName = (String) json.get("ToUserName");
        try {
            result = UnicomRobot.getAnswer(content, wum.getWxUser().getId().toString( ));
            WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName()).getOperator().sendTxtMessage(toUserName, result);
            wum.setHandleTime(new Date());
            wum.setHandleResult("�ظ��û���"+result);
            wxUserMsgDao.save(wum);
        } catch (Exception e) {
            e.printStackTrace();
            //TODO log;
        }        
        return result;
    }
}
