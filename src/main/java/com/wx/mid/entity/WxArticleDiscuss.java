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
@Table(name = "wx_article_discuss")

public class WxArticleDiscuss implements Serializable {
    private static final long serialVersionUID = -2051285227021103021L;
   
    @Id
    @Column(nullable = false)
    private BigDecimal id;

    @Column(name = "USER_ID", nullable = false)
    private long userId;

 
    @Column(name = "ARTICLE_ID")
    private BigDecimal artId;
    
    @Column(name = "CONTENT")
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "discuss_date", nullable = false)
    private Date discussDate;

   

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


    

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDiscussDate(Date discussDate) {
        this.discussDate = discussDate;
    }

    public Date getDiscussDate() {
        return discussDate;
    }
}

