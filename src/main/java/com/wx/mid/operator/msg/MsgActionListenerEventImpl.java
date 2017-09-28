package com.wx.mid.operator.msg;

import java.io.PrintWriter;

import java.util.Date;


import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactory;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxUserMsgDao;
import com.wx.mid.operator.msg.evt.EventActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component("eventMsgActionListener")

public class MsgActionListenerEventImpl implements MsgActionListener {
    public MsgActionListenerEventImpl() {
        super();
    }
    @Autowired
    WxUserMsgDao wxUserMsgDao;
    //@Override
    public String doAction(WxUserMsg wum, PrintWriter out) {
        //System.out.println("����Event����");
        WxBeanFactory wbf= WxBeanFactoryImpl.getInstance();
        String evtActionListenerName=null;
        if(wum.getEvtType()!=null){
            evtActionListenerName=wum.getEvtType().getHandleClassName();
        }
        //System.out.println("*****EvtActionListener���أ�"+wum.getEvtType().getHandleClassName());
        EventActionListener eal=wbf.getEvtActionListener(evtActionListenerName);
        eal.doAction(wum);
        wum.setHandleTime(new Date());
        wxUserMsgDao.save(wum);
        return null;
    }
    
    public static void main(String[] args) {
       ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
       WxUserMsgDao wum = (WxUserMsgDao) ctx.getBean("wxUserMsgDao");
       //System.out.println(wum.findById(new BigDecimal(10349)).getEvtType().getHandleClassName());
   }

    @Override
    public String doAction(WxUserMsg wum) {
        //System.out.println("����Event����");
        WxBeanFactory wbf= WxBeanFactoryImpl.getInstance();
        String evtActionListenerName=null;
        if(wum.getEvtType()!=null){
            evtActionListenerName=wum.getEvtType().getHandleClassName();
        }
        //System.out.println("*****EvtActionListener���أ�"+wum.getEvtType().getHandleClassName());
        EventActionListener eal=wbf.getEvtActionListener(evtActionListenerName);
        eal.doAction(wum);
        wum.setHandleTime(new Date());
        wxUserMsgDao.save(wum);
        return null;       
    }
}
