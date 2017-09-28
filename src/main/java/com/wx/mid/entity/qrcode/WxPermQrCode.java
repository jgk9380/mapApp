package com.wx.mid.entity.qrcode;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
//@Inheritance()
//@DiscriminatorColumn(name = "DEST_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("QR_LIMIT_SCENE")
//action_name in('QR_LIMIT_SCENE','QR_SCENE')
public  class WxPermQrCode extends WxQrCode implements Serializable {
    @SuppressWarnings("compatibility:2369195362108559900")
    private static final long serialVersionUID = 3515168496892269730L;

    public WxPermQrCode() {
        super();
    }

    @Override
    public boolean isValid() {       
        return true;
    }
}
