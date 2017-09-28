package com.wx.mid.dao;

import java.math.BigDecimal;


import com.wx.mid.entity.WxPerQrCode;
import org.springframework.data.repository.CrudRepository;


public interface WxPerQrCodeDao extends CrudRepository<WxPerQrCode, String> {    
    WxPerQrCode findByTicket(String ticket);
    WxPerQrCode findByUserId(long userId);
    WxPerQrCode findBySencenId(BigDecimal sencenId);
}

