package com.dao.s;

import com.entity.s.StockPromotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by jianggk on 2016/12/26.
 */
public interface StockPromotionDao extends JpaRepository<StockPromotion,Long> {
    StockPromotion findById(Long id);
    List<StockPromotion> findByTele(String tele);
}
