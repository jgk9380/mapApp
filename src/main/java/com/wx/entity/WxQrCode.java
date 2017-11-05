package com.wx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wx_qrcode")
public class WxQrCode {
    @Id
    @Column
    Integer id;
    @Column
    String url;
    @Column
    String ownerId;//empId
    @Column
    Integer wxUserId;
    @Column
    Integer pictId;

    public Integer getPictId() {
        return pictId;
    }

    public void setPictId(Integer pictId) {
        this.pictId = pictId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxQrCode wxQrCode = (WxQrCode) o;

        return id != null ? id.equals(wxQrCode.id) : wxQrCode.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Integer wxUserId) {
        this.wxUserId = wxUserId;
    }

}
