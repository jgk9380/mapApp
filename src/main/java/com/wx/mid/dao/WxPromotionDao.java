package com.wx.mid.dao;

import java.util.List;


import com.wx.mid.entity.WxPromotion;
import org.springframework.data.repository.CrudRepository;

public interface WxPromotionDao extends CrudRepository<WxPromotion, Integer> {
    WxPromotion findById(int id);
    List<WxPromotion> findAll();
}
