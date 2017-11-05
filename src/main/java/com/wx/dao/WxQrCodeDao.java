package com.wx.dao;

import com.wx.entity.WxQrCode;
import org.springframework.data.repository.CrudRepository;

public interface WxQrCodeDao extends CrudRepository<WxQrCode,Integer> {
}
