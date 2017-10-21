package com.wx.mid.handle;

import com.wx.dao.WxEventDao;
import com.wx.dao.WxUserDao;
import com.wx.entity.WxEvent;
import com.wx.mid.base.util.MessageUtil;
import com.wx.mid.operator.WxManager;
import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WxEventListener implements ApplicationListener<WxEventSpringEvent> ,CommandLineRunner ,BeanFactoryAware ,ApplicationEventPublisherAware {

    @Autowired
    WxManager wxManager;
    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxUtils wxUtils;
    @Autowired
    WxEventDao wxEventDao;

    @Override
    public void onApplicationEvent(WxEventSpringEvent wxEventSpringEvent) {
        WxEvent wxEvent= (WxEvent) wxEventSpringEvent.getSource();
        System.out.printf("接收到event id="+wxEvent.getId());
        dispWxEvent(wxEvent);
    }

    public void dispWxEvent(WxEvent wxEventSource) {
        String respXml = null;
        try {
            WxEvent wxEvent = wxEventDao.findById(wxEventSource.getId());
            if(wxEvent.getFlag()!=0) {
                System.out.printf("已处理 eventId="+wxEvent.getId());
                return;
            }
            System.out.printf("--content="+wxEvent.getContent());
            JSONObject json = JSONObject.fromObject(wxEvent.getContent());
            //            // 发送方帐号
            //            String fromUserName = json.getString("FromUserName");
            //            // 开发者微信号
            //            String toUserName = json.getString("ToUserName");
            // 消息类型
                        String msgType = json.getString("MsgType");
            //            TextMessage textMessage = new TextMessage();
            //            textMessage.setToUserName(fromUserName);
            //            textMessage.setFromUserName(toUserName);
            //            textMessage.setCreateTime(new Date().getTime());
            //            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            if(msgType.equals(MessageUtil.RESP_MESSAGE_TYPE_TEXT)){
               this.beanFactory.getBean(TextEventHandle.class).handleEvent(wxEvent);
            }
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                this.beanFactory.getBean(EventEventHandle.class).handleEvent(wxEvent);
            }
            wxEvent.setFlag(1);
            wxEvent.setDispDate(new Date());
            wxEventDao.save(wxEvent);
        } catch (Exception e) {
            System.out.printf("\n--系统error at");
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
                //System.out.println("--WxManagerImpl.run()  appId=" + this.appName);
                //todo 方便测试代码
                while(true) {
                    List<WxEvent> l = wxEventDao.findByDispDateIsNull();
                    l.stream().forEach(w -> this.applicationEventPublisher.publishEvent(new WxEventSpringEvent(w)));
                    Thread.sleep(3000);
                }
    }
    BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
