package com.wx.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wx_manual_message")
public class WxManualMessage {
    @Id
    @Column
    int id;
    @Column
    Integer wxUserId;
    @Column
    String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    Date receivedDate;
    @Column
    String replyContent;

    @Column
    String replyer;
    @Column
    int  replyFlag;//REPLE_FLAG

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxManualMessage that = (WxManualMessage) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(Integer wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer;
    }

    public int getReplyFlag() {
        return replyFlag;
    }

    public void setReplyFlag(int replyFlag) {
        this.replyFlag = replyFlag;
    }
}
