package com.wx.mid.operator;

import com.wx.mid.entity.WxUser;
import com.wx.mid.entity.WxUserMsg;

import java.util.Map;





public interface WxManager {
    WxOperator getOperator();
    WxUser getWxUser(String openId);  //getWxUserBo���
}
