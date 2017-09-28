package com.wx.mid.operator.data.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


public interface DataServiceListener {
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws  Exception;
}
