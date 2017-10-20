package com.wx.dao;

import com.wx.entity.WxApp;
import com.wx.entity.WxEvent;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WxEventDao extends CrudRepository<WxEvent, Integer> {
    WxEvent findById(int id);
    List<WxEvent> findByDispDateIsNull();

}
