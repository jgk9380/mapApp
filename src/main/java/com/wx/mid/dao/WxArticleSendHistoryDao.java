package com.wx.mid.dao;


import com.wx.mid.entity.WxArticleSendHistory;
import com.wx.mid.entity.WxArticleSendHistoryPK;
import org.springframework.data.repository.CrudRepository;

public interface WxArticleSendHistoryDao extends CrudRepository<WxArticleSendHistory, WxArticleSendHistoryPK> {
    
}

