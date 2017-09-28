package com.wx.mid.dao;



import com.wx.mid.entity.WxEventType;
import org.springframework.data.repository.CrudRepository;

public interface WxEvtTypeDao extends CrudRepository<WxEventType, String>{
    WxEventType findById(String id);
}