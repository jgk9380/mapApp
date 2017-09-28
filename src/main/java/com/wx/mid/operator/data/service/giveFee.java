package com.wx.mid.operator.data.service;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.wx.mid.WxSession;
import com.wx.mid.util.UserService;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;



@Component("giveFee")
public class giveFee implements DataServiceListener {
    public giveFee() {
        super();
    }

    @Autowired
    UserService userService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                             IOException {
        String param = request.getParameter("param");
        //System.out.println("param=" + param);
        JSONObject paramJson = JSONObject.fromObject(param);
        String giveFee = (String) paramJson.get("giveFee");
        String tele = (String) paramJson.get("tele");
        //System.out.println("tele="+tele+" give");        
        WxSession wxSession = (WxSession) request.getSession(true).getAttribute("wxSession");
        return wxSession.giveFee(tele, Double.parseDouble(giveFee));
    }
}

