package com.wx.mid.operator.msg.evt;


import com.wx.mid.entity.WxUserMsg;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;


@Component("defaultEvtActionListenBaseImpl")

public class EventActionListenerDefaultImpl implements EventActionListener{
    public EventActionListenerDefaultImpl() {
        super();
    }
  
    @Override
    public void doAction(WxUserMsg wum) {
        Logger.getLogger(EventActionListenerDefaultImpl.class).error("EventActionListener");
    }
}

