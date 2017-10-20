package com.wx.mid.operator;

import com.wx.dao.WxEventDao;
import com.wx.entity.WxEvent;
import com.wx.mid.util.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Test implements  CommandLineRunner {
    @Autowired
    WxUtils wxUtils;
    @Autowired
    WxEventDao wxEventDao;
    @Override
    public void run(String... strings) throws Exception {
        //testInsertWxEvent();
    }
    void testInsertWxEvent(){
        WxEvent wxEvent=new WxEvent();
        wxEvent.setId(wxUtils.getSeqencesValue().intValue());
        wxEvent.setContent("test");
        wxEventDao.save(wxEvent);
    }
}
