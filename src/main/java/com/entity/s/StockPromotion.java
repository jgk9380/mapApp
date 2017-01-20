package com.entity.s;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jianggk on 2016/12/26.
 */
@Entity
@Table(name = "stock_promotion_data")
public class StockPromotion {
    @Id
    Long id;
    @Column(nullable = false)
    String tele;
    @Column(nullable = false)
    String promotionName;
    @Column(nullable = false)
    String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
