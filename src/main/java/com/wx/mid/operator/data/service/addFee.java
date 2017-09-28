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




@Component("addFee")
public class addFee implements DataServiceListener {
    public addFee() {
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
        String addFee = (String) paramJson.get("addFee");
        WxSession wxSession = (WxSession) request.getSession(true).getAttribute("wxSession");
        double toAddFee = Double.parseDouble(addFee);
        return wxSession.addFee((int) toAddFee);
    }
}

