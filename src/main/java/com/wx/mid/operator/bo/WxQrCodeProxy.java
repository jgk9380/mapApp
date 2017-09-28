package com.wx.mid.operator.bo;


import com.wx.mid.entity.qrcode.WxQrCode;

public interface WxQrCodeProxy {
    WxQrCode getUserQrCode(long userId);

    WxQrCode getCouponQrCode(long couponQrCode);
}
