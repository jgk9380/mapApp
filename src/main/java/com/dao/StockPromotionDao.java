package com.dao;

import com.entity.StockPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jianggk on 2016/12/26.
 */
public interface StockPromotionDao extends JpaRepository<StockPromotion,Long> {
    StockPromotion findById(Long id);
    List<StockPromotion> findByTele(String tele);
}
