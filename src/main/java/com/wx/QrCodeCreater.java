package com.wx;

import com.wx.dao.WxPermQrCodeDao;
import com.wx.entity.WxPermQrCode;
import com.wx.mid.operator.WxManager;
import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QrCodeCreater {

    @Autowired
    WxPermQrCodeDao wxPermQrCodeDao;
    @Autowired
    WxManager wxManager;
    @Autowired
    WxUtils wxUtils;

    private void createPermQrCode() {
        System.out.println("\n createQrCode \n");
        Integer currentSceneId = wxPermQrCodeDao.queryMaxSceneId();
        System.out.println("\n -------currentSceneId=" + currentSceneId);
        if (null == currentSceneId) {
            currentSceneId = 0;
        }
        JSONObject json = wxManager.getWxOperator().createPermanentQRCode(currentSceneId + 1);
        System.out.println("\n---qrCodejson=" + json.toString());
        WxPermQrCode wxPermQrCode = new WxPermQrCode();
        wxPermQrCode.setId(wxUtils.getSeqencesValue().intValue());
        wxPermQrCode.setSceneId(currentSceneId+1);
        wxPermQrCode.setTicket((String) json.get("ticket"));
        wxPermQrCode.setUrl((String) json.get("url"));
        wxPermQrCodeDao.save(wxPermQrCode);
    }

    public void create500PermQrCode(){
        for(int i=0;i<500;i++)
            this.createPermQrCode();
    }

}
