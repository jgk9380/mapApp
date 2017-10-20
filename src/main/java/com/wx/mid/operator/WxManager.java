package com.wx.mid.operator;

import com.wx.entity.WxUser;


public interface WxManager {
    //WxOperator getOperator();
    boolean checkSignature(String signature, String timestamp,String nonce);
    WxUser getWxUser(String openId);  //
}
