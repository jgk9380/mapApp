package com.wx.mid.dao;

import java.math.BigDecimal;


import com.wx.mid.entity.WxSmsLog;
import org.springframework.data.repository.CrudRepository;

public interface WxSmsLogDao extends CrudRepository<WxSmsLog, BigDecimal>{
  
}
