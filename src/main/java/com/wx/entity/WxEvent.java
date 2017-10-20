package com.wx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "wx_event")
public class WxEvent implements Serializable {
    private static final long serialVersionUID = 6069869518886502253L;

    @Id
    @Column(nullable = false, length = 20)
    private Integer id;
    @Column(length = 200)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    Date occureDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    Date dispDate;
    @Column()
    String dispResult;
    @Column()
    int flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getOccureDate() {
        return occureDate;
    }

    public void setOccureDate(Date occureDate) {
        this.occureDate = occureDate;
    }

    public Date getDispDate() {
        return dispDate;
    }

    public void setDispDate(Date dispDate) {
        this.dispDate = dispDate;
    }

    public String getDispResult() {
        return dispResult;
    }

    public void setDispResult(String dispResult) {
        this.dispResult = dispResult;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public WxEvent() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WxEvent wxEvent = (WxEvent) o;

        return id != null ? id.equals(wxEvent.id) : wxEvent.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
