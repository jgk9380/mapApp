package com.wx.mid.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wx_event_type")
public class WxEventType implements Serializable {
    private static final long serialVersionUID = 6069869518886502253L;
    @Column(name = "HANDLE_CLASS_NAME", length = 200)
    private String handleClassName;
    @Id
    @Column(nullable = false, length = 20)
    private String id;
    @Column(length = 200)
    private String remark;
  
    private boolean defer;
   

    public WxEventType() {
    }

  

    public String getHandleClassName() {
        return handleClassName;
    }

    public void setHandleClassName(String handleClassName) {
        this.handleClassName = handleClassName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public void setDefer(boolean defer) {
        this.defer = defer;
    }

    public boolean isDefer() {
        return defer;
    }
}
