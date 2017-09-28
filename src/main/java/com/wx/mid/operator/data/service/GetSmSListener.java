package com.wx.mid.operator.data.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.mid.WxSession;
import com.wx.mid.util.UserService;
import net.sf.json.JSONObject;


import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component("sendsmscode")
public class GetSmSListener implements DataServiceListener {
    public GetSmSListener() {
        super();
    }
    @Autowired
    UserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String param = request.getParameter("param");
        //System.out.println("param=" + param);
        JSONObject paramJson = JSONObject.fromObject(param);
        String tele = (String) paramJson.get("tele");
        WxSession wxSession = (WxSession) request.getSession(true).getAttribute("wxSession");
        if (wxSession == null) {
            JSONObject json = new JSONObject();
            json.put("result", "error");
            json.put("resultInfo", "ϵͳ����:wxn");
            Logger.getLogger(GetSmSListener.class).error("wxUser is null");
            return json;
        }
        
        return wxSession.sendSmsCode(tele);
    }
}
