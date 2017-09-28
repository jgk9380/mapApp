package com.wx.mid.dao;


import com.wx.mid.entity.WxMsgType;
import org.springframework.data.repository.CrudRepository;

public interface WxMsgTypeDao extends CrudRepository<WxMsgType, String>{
    WxMsgType findById(String id);
}
