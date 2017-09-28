package com.wx.mid.entity;

import java.io.Serializable;

import java.math.BigDecimal;

public class WxArticleSendHistoryPK implements Serializable {
    private BigDecimal userId;
    private BigDecimal artId;

    public WxArticleSendHistoryPK() {
    }

  
    public boolean equals(Object other) {
        if (other instanceof WxArticleSendHistoryPK) {
            final WxArticleSendHistoryPK otherWxArticleSendHistoryPK = (WxArticleSendHistoryPK) other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public void setArtId(BigDecimal artId) {
        this.artId = artId;
    }

    public BigDecimal getArtId() {
        return artId;
    }


}
