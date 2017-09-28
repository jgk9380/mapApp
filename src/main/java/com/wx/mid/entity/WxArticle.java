package com.wx.mid.entity;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ @NamedQuery(name = "WxArticle.findAll", query = "select o from WxArticle o") })
@Table(name = "WX_ARTICLE")
public class WxArticle implements Serializable {
    private static final long serialVersionUID = -5840628431225485807L;
    @Id
    @Column(nullable = false)
    private BigDecimal id;

    @Column(name = "DESCC", length = 200)
    private String articleDesc; 
    @Column(name = "BIT_FLAG")
    private String bitFlag;
    @Column(name = "CONTENT_HTML")
    private String contentHtml;
    @Column(name = "URL", length = 200)
    private String contentUrl;
    @Temporal(TemporalType.DATE)
    @Column(name = "LIMIT_DATE")
    private Date limitDate;
    @Column(name = "PIC_URL", length = 200)
    private String picUrl;
    @Temporal(TemporalType.DATE)
    @Column(name = "SEND_DATE")
    private Date sendDate;
    @Column(length = 200)
    private String title;
    private int seq;

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    public WxArticle() {
    }

    public WxArticle(String articleDesc, String bitFlag, String contentHtml, String contentUrl, BigDecimal id,
                     String levelOne, String levelTwo, Date limitDate, String picUrl, Date sendDate, String title) {
        this.articleDesc = articleDesc;
        this.bitFlag = bitFlag;
        this.contentHtml = contentHtml;
        this.contentUrl = contentUrl;
        this.id = id;
        this.limitDate = limitDate;
        this.picUrl = picUrl;
        this.sendDate = sendDate;
        this.title = title;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getBitFlag() {
        return bitFlag;
    }

    public void setBitFlag(String bitFlag) {
        this.bitFlag = bitFlag;
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  
}
