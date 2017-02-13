package com.control.old;

import com.dao.s.StockPromotionDao;
import com.entity.s.StockPromotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jianggk on 2016/12/26.
 */
@RestController
@RequestMapping("/StockPromotion")
public class StockPromotionControl {
    @Autowired
    StockPromotionDao stockPromotionDao;
    @RequestMapping(path="/byTele/{tele}",method = RequestMethod.GET)
    public List<StockPromotion> getStockPromotion(@PathVariable("tele") String tele){
        List<StockPromotion> stockPromotionList= stockPromotionDao.findByTele(tele);
        return stockPromotionList;
    }
}
