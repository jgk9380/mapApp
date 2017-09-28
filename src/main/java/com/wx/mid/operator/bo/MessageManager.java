package com.wx.mid.operator.bo;

public interface MessageManager {

    String getPassiveSendTextXml(String fromUser, String toUser, String Content);
    String getPassiveSendArticleXml(String fromUser, String toUser, String title, String desc, String pictureUrl, String contentUrl);
    
}

//SystemMessage ϵͳ���͸��û�����Ϣ
//UserMessage   �û���Ϊ���͸�ϵͳ����Ϣ

