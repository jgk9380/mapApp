package com.wx.mid.entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "WxArticleSendHistory.findAll", query = "select o from WxArticleSendHistory o") })
@Table(name = "WX_ARTICLE_SEND_HISTORY")
@IdClass(WxArticleSendHistoryPK.class)
public class WxArticleSendHistory implements Serializable {
    private static final long serialVersionUID = -2051285227021103021L;
    @Temporal(TemporalType.DATE)
    @Column(name = "SEND_DATE", nullable = false)
    private Date sendDate;
    @Id
    @Column(name = "USER_ID", nullable = false)
    private BigDecimal userId;

    @Id
    @Column(name = "ART_ID")
    private BigDecimal artId;

    public WxArticleSendHistory() {
    }

   


    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
