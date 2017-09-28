package com.wx.mid.operator;

import java.math.BigDecimal;

import java.util.Date;


import com.wx.mid.entity.WxPromotionGift;
import com.wx.mid.entity.WxUser;
import com.wx.mid.dao.WxAssertDao;
import com.wx.mid.dao.WxPromotionGiftDao;
import com.wx.mid.dao.WxUserDao;
import com.wx.mid.util.WxUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component("promotionManager")

public class PromotionManager {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    WxPromotionGiftDao wxPromotionGiftDao;

    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxAssertDao wxAssertDao;
    //TODO   �ú�����Ҫ�������
    public boolean ggkIncomeGift(long userId, String giftId,String ipAddress) {
        //System.out.println("-----ipAddress=" + ipAddress);
        WxUser wxUser = wxUserDao.findById(userId);
        
        WxPromotionGift pg = wxPromotionGiftDao.findById(giftId);
        { //TODO ����Ƿ��Ѿ�������
            pg.setGainDate(new Date());
            pg.setGainWay("�ιο�");
            pg.setGainer(wxUser);
            pg.setStatus(2);
            pg.setIpAddress(ipAddress);
            wxPromotionGiftDao.save(pg);
        }
        if (pg.getAssertType().getId().intValue() == 0 || pg.getAssertType() == null) {
            return true;
        }
        BigDecimal id = WxUtils.getSeqencesValue();
        WxAssert war = new WxAssert();
        war.setId(id);
        war.setAssertType(pg.getAssertType());
        war.setWxUser(wxUser);
        war.setFaceValue(pg.getFaceVaule());
        war.setWxPromotionGift(pg);
        war.setOccurDate(new Date());
        war.setRemark("���Թιο������");
       
        wxAssertDao.save(war);
        return true;
    }
}
