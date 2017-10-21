package com.wx.mid.handle;

import com.wx.entity.WxEvent;
import org.springframework.context.ApplicationEvent;

public class WxEventSpringEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    public WxEventSpringEvent(WxEvent source) {
        super(source);
    }
}
