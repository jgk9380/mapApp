package com.wx.web.mvc;

import java.util.Date;


import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxTestSpeedLogDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/system")
public class SystemControl {
    public SystemControl() {
        super();
    }
    
    @RequestMapping(value = "/refreshData", method = RequestMethod.GET)
    @ResponseBody
    public  String refreshData() {  
        WxTestSpeedLogDao wtsld = WxBeanFactoryImpl.getInstance().getBean("wxTestSpeedLogDao", WxTestSpeedLogDao.class);
        wtsld.refershData();
       return "success refresh data at " +new Date();
    }
}
