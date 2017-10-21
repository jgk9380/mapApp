package com.wx.mid.handle;

import com.wx.entity.WxEvent;

public interface EventHandle {
    void handleEvent(WxEvent wxEvent);
}
