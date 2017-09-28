package com.wx.mid.operator.data.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.mid.WxSession;
import net.sf.json.JSONObject;


import org.springframework.stereotype.Component;



@Component("bindtele")
public class bindListener implements DataServiceListener {
    public bindListener() {
        super();
    }


    @Override
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param"); 
        JSONObject paramJson = JSONObject.fromObject(param);     
        String yzm = paramJson.getString("yzm");     
        String tele = paramJson.getString("tele");
        WxSession wxSession = (WxSession) request.getSession(true).getAttribute("wxSession");
        return wxSession.bindTele(tele, yzm);
    }
}
