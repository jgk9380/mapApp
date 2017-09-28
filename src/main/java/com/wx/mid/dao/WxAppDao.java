package com.wx.mid.dao;


import com.wx.mid.entity.WxApp;
import org.springframework.data.repository.CrudRepository;
//import mid.springframework.stereotype.Repository;


//@Repository("ied")
//@Transactional
public interface WxAppDao extends CrudRepository<WxApp, String> {
    
    WxApp findById(String id);

    WxApp findByAppName(String name);
    
    WxApp findByUserName(String userName);
}
