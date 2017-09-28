package com.wx.mid.entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WX_ARTICLE_READ_HISTORY")

public class WxArticleReadHistory implements Serializable {
    private static final long serialVersionUID = -2051285227021103021L;
   
    @Id
    @Column(nullable = false)
    private BigDecimal id;

    @Column(name = "USER_ID", nullable = false)
    private long userId;
    @Column(name = "sender", nullable = false)
    private long sender;
 
    @Column(name = "ARTICLE_ID")
    private BigDecimal artId;
   
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "READ_DATE", nullable = false)
    private Date readDate;

   

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setArtId(BigDecimal artId) {
        this.artId = artId;
    }

    public BigDecimal getArtId() {
        return artId;
    }


    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setSender(long sender) {
        this.sender = sender;
    }

    public long getSender() {
        return sender;
    }
}
