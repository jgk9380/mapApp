package com.wx.mid.operator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;


import com.wx.mid.entity.WxPromotion;
import com.wx.mid.entity.WxPromotionGift;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxPromotionDao;
import com.wx.mid.dao.WxPromotionGiftDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@Component("defaultPromotionGiftSelector")
public class PromotionGiftSelectorDefaultImpl extends PromotionGiftSelectorBaseImpl {
    public PromotionGiftSelectorDefaultImpl() {
        super();
    }
    @Autowired
    JdbcTemplate jdbcTemplate; // = WxBeanFactoryImpl.getInstance().getJdbcTemplate();
    @Autowired
    WxPromotionDao wxPromotionDao; // = WxBeanFactoryImpl.getInstance().getBean("wxPromotionDao", WxPromotionDao.class);
    @Autowired
    WxPromotionGiftDao wxPromotionGiftDao;


    @Override
    //TODO �������
    //@Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    //@Transactional(isolation = Isolation.SERIALIZABLE)
    //    public WxPromotionGift getGiftId(long userId, int promotionId) {
    //        if (!isUserAllowJoioned(userId, promotionId))
    //            return null;
    //        WxPromotion wp = wxPromotionDao.findById(promotionId);
    //        String sql = wp.getGiftSelectSql();
    //        Map<String, Object> params = new HashMap<>();
    //        params.put("userId", userId);
    //        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
    //        List<String> gfitIdList = npjt.queryForList(sql, params, String.class);
    //        if (gfitIdList.size() == 0)
    //            return null;
    //        for (int i = 0; i < 10; i++) {
    //            Date currentDate = new Date();
    //            int rand = (int) (currentDate.getTime() % gfitIdList.size());
    //            String giftId = gfitIdList.get(rand);
    //            //���ݵ�ǰ�������ȡGift����ΪԤռ��״̬
    //            WxPromotionGift wpg = super.preOccupy(giftId);
    //            if (wpg != null) {
    //                return wpg;
    //            }
    //        }
    //
    //        return null;
    //    }

    //@Transactional(isolation = Isolation.SERIALIZABLE)
    public WxPromotionGift getGiftId(long userId, int promotionId) {
        if (!isUserAllowJoioned(userId, promotionId))
            return null;
        WxPromotion wp = wxPromotionDao.findById(promotionId);
        String sql = wp.getGiftSelectSql();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<String> gfitIdList = npjt.queryForList(sql, params, String.class);
        if (gfitIdList.size() == 0)
            return null;
        for (int i = 0; i < 10; i++) {
            Date currentDate = new Date();
            int rand = (int) (currentDate.getTime() % gfitIdList.size());
            String giftId = gfitIdList.get(rand);
            //���ݵ�ǰ�������ȡGift����ΪԤռ��״̬
            //WxPromotionGift wpg = super.preOccupy(giftId);
            WxPromotionGift wpg = super.preOccupy(giftId);
            if (wpg != null) {
                return wpg;
            }
        }
        return null;
    }


    protected boolean isUserAllowJoioned(long userId, int promotionId) {
        // TODO Implement this method
        if (super.isUserAllowJoioned(userId, promotionId) == false)
            return false;

        WxPromotion wp = wxPromotionDao.findById(promotionId);
        String sql = wp.getAllowJoinSql();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(jdbcTemplate);
        List<Long> allowUserIds = npjt.queryForList(sql, params, Long.class);
        if (allowUserIds.size() == 1) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        PromotionGiftSelectorDefaultImpl pi =
            WxBeanFactoryImpl.getInstance().getBean("defaultPromotionGiftSelector",
                                                    PromotionGiftSelectorDefaultImpl.class);
        for (int i = 0; i < 10; i++) {
            Date d1 = new Date();
            pi.getGiftId(3007095, 3);
            Date d2 = new Date();
            System.out.println(d1.getTime() - d2.getTime());
        }
    }

}
