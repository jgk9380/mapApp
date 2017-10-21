package com.wx.mid.handle;

import com.wx.entity.WxEvent;
import com.wx.mid.base.message.req.TextMessage;
import com.wx.mid.base.message.resp.Article;
import com.wx.mid.base.message.resp.NewsMessage;
import com.wx.mid.base.util.MessageUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class EventEventHandle  implements EventHandle,BeanFactoryAware {
    @Override
    public void handleEvent(WxEvent wxEvent) {
        System.out.printf("EventEventHandle");
        String respXml = null;
        JSONObject json = JSONObject.fromObject(wxEvent.getContent());
        // 发送方帐号
        String fromUserName = json.getString("FromUserName");
        // 开发者微信号
        String toUserName = json.getString("ToUserName");
        // 消息类型
        String msgType = json.getString("MsgType");
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        String eventType = json.getString("Event");
        // 订阅
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            this.beanFactory.getBean(SubscripeHandle.class).handleEvent(wxEvent);
        }
        // 取消订阅
        if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
            // TODO 暂不做处理,wxUser 标志
            wxEvent.setDispResult("--用户状态变更");
            return;
        }
        // 自定义菜单点击事件
        if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
            System.out.printf("---菜单事件");
            // 事件KEY值，与创建菜单时的key值对应
            String eventKey = json.getString("EventKey");
            // 根据key值判断用户点击的按钮
            if (eventKey.equals("oschina")) {
                Article article = new Article();
                article.setTitle("中国联通欢迎你");
                article.setDescription("中国联通服务好、\n中国联通服务优");
                article.setPicUrl("");
                article.setUrl("http://www.10010.com");
                List<Article> articleList = new ArrayList<Article>();
                articleList.add(article);
                // 创建图文消息
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setToUserName(fromUserName);
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setArticleCount(articleList.size());
                newsMessage.setArticles(articleList);
                respXml = MessageUtil.messageToXml(newsMessage);
            } else if (eventKey.equals("iteye")) {
                textMessage.setContent("test iteye");
//                        respXml = MessageUtil.messageToXml(textMessage);
                respXml = MessageUtil.messageToXml(textMessage);

            }
        }
    }
    BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.              beanFactory=beanFactory;
    }
}
