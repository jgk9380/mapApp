package com.wx.web.servlet;


import com.wx.mid.base.message.req.TextMessage;
import com.wx.mid.base.message.resp.Article;
import com.wx.mid.base.message.resp.NewsMessage;
import com.wx.mid.base.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



public class CoreService {
    /**
     * ����΢�ŷ���������
     *
     * @param request
     * @return xml
     */
    public static String processRequest(HttpServletRequest request) {
        // xml��ʽ����Ϣ����
        String respXml = null;
        try {
            // ����parseXml��������������Ϣ
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // ���ͷ��ʺ�
            String fromUserName = requestMap.get("FromUserName");
            // ������΢�ź�
            String toUserName = requestMap.get("ToUserName");
            // ��Ϣ����
            String msgType = requestMap.get("MsgType");
           

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            // �¼�����
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // �¼�����
                String eventType = requestMap.get("Event");
                // ����
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    textMessage.setContent("���ã���ӭ�γ�ͨ��Ȧ");
                    // ����Ϣ����ת����xml
                    respXml = MessageUtil.messageToXml(textMessage);
                }
                // ȡ������
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO �ݲ�������
                }
                // �Զ���˵�����¼�
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // �¼�KEYֵ���봴���˵�ʱ��keyֵ��Ӧ
                    String eventKey = requestMap.get("EventKey");
                    // ����keyֵ�ж��û�����İ�ť
                    if (eventKey.equals("oschina")) {
                        Article article = new Article();
                        article.setTitle("�й���ͨ��ӭ��");
                        article.setDescription("�й���ͨ����á�\n�й���ͨ������");
                        article.setPicUrl("");
                        article.setUrl("http://www.10010.com");
                        List<Article> articleList = new ArrayList<Article>();
                        articleList.add(article);
                        // ����ͼ����Ϣ
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
                        respXml = MessageUtil.messageToXml(textMessage);
                    }
                }
            }
            // ���û�����Ϣʱ
            else {
                textMessage.setContent("��ͨ���˵�ʹ����ַ��������");
                respXml = MessageUtil.messageToXml(textMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respXml;
    }
}

