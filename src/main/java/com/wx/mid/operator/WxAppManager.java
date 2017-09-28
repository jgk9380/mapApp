package com.wx.mid.operator;

import com.wx.mid.entity.WxUser;
import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.operator.bo.WxUserBo;

import java.util.Map;





public interface WxAppManager {
    WxOperator getOperator();   
    
    WxUserBo getWxUserBo(String openId);
    
    WxUser getWxUser(String openId);  //getWxUserBo���
    
    WxUserMsg saveUserMsg(Map<String, String> argsMap);
   
    void dispUserMsg(WxUserMsg wum);   
    
    void sendWxMail(long receiveUserId, String cont, long sendUserId);
    
    void sendWxArticle(long userId, String title, String desc, String picUrl, String url);
  
}
