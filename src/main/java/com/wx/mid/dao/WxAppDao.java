package com.wx.mid.dao;


import com.wx.mid.entity.WxApp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
//import mid.springframework.stereotype.Repository;



public interface WxAppDao extends CrudRepository<WxApp, String> {
    
    WxApp findById(String id);

    WxApp findByAppName(String name);
    
    WxApp findByUserName(String userName);
}
