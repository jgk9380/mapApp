package com.wx.mid.operator.msg;


import com.wx.mid.entity.WxUserMsg;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Component("defaultMsgActionListener")
public class MsgActionListenerDefaultImpl implements MsgActionListener {
    public MsgActionListenerDefaultImpl() {
        super();
    }
  
    @Override
    public String doAction(WxUserMsg wum) {
        Logger.getLogger(MsgActionListenerDefaultImpl.class).error("û�ж�Ӧ��MsgActionListener");
        return null;
    }
}
