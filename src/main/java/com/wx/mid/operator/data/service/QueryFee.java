package com.wx.mid.operator.data.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.wx.mid.WxSession;
import net.sf.json.JSONObject;


import org.springframework.stereotype.Component;




@Component("queryFee")
public class QueryFee implements DataServiceListener {
    public QueryFee() {
        super();
    }

    @Override
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
                                                                                             IOException {
        String param = request.getParameter("param");
        //System.out.println("param=" + param);  
        WxSession wxSession = (WxSession) request.getSession().getAttribute("wxSession");
        return wxSession.getLeaveFee();
    }
}

