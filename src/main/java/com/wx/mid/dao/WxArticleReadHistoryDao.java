package com.wx.mid.dao;

import java.math.BigDecimal;


import com.wx.mid.entity.WxArticleReadHistory;
import org.springframework.data.repository.CrudRepository;


public interface WxArticleReadHistoryDao extends CrudRepository<WxArticleReadHistory, BigDecimal> {
 
}
