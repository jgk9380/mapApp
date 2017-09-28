package com.wx.mid.operator.msg;

import com.wx.mid.entity.WxUserMsg;


public interface MsgActionListener {
    //String doAction(WxUserMsg wum,PrintWriter out);
    String doAction(WxUserMsg wum);
}
