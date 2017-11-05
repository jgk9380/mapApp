package com.wx;

import com.google.zxing.WriterException;
import com.wx.dao.WxPermQrCodeDao;
import com.wx.dao.WxQrCodeDao;
import com.wx.dao.WxResourceDao;
import com.wx.entity.WxPermQrCode;
import com.wx.entity.WxQrCode;
import com.wx.entity.WxResource;
import com.wx.mid.operator.WxManager;
import com.wx.mid.util.WxUtils;
import com.wx.qrcodetools.QRCodeUtil;
import net.sf.json.JSONObject;
import org.apache.tomcat.jni.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Component
public class QrCodeCreater {

    @Autowired
    WxPermQrCodeDao wxPermQrCodeDao;
    @Autowired
    WxManager wxManager;
    @Autowired
    WxUtils wxUtils;
    @Autowired
    WxResourceDao wxResourceDao;
    @Autowired
    WxQrCodeDao wxQrCodeDao;
    String qrCodeUrlTemplate="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http://www.cu0515.com/myqrcode.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect ";

    private void createPermQrCode() {
        System.out.println("\n createQrCode \n");
        Integer currentSceneId = wxPermQrCodeDao.queryMaxSceneId();
        System.out.println("\n -------currentSceneId=" + currentSceneId);
        if (null == currentSceneId) {
            currentSceneId = 0;
        }
        int nextCurrentSceneId=currentSceneId + 1;
        if(nextCurrentSceneId>=100000){
            System.out.println("\n-------------- 永久二维码已满，请整理二维码  ------------- \n ");
            return;
        }

        JSONObject json = wxManager.getWxOperator().createPermanentQRCode(nextCurrentSceneId);
        System.out.println("\n---qrCodejson=" + json.toString());
        WxPermQrCode wxPermQrCode = new WxPermQrCode();
        wxPermQrCode.setId(wxUtils.getSeqencesValue().intValue());
        wxPermQrCode.setSceneId(currentSceneId+1);
        wxPermQrCode.setTicket((String) json.get("ticket"));
        wxPermQrCode.setUrl((String) json.get("url"));
        wxPermQrCodeDao.save(wxPermQrCode);
    }
    @Transactional
    public  void createWxQrCode() throws WriterException, IOException {
        int id=wxUtils.getSeqencesValue().intValue();
        String url=qrCodeUrlTemplate.replace("STATE",""+id);
        BufferedImage image= QRCodeUtil.zxingCodeCreateImage(url,500,500,"jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean flag = ImageIO.write(image, "jpg", out);
        byte[] b = out.toByteArray();

        WxResource wxResource=new WxResource();
        int resourceId=wxUtils.getSeqencesValue().intValue();
        wxResource.setId(resourceId);
        wxResource.setResourceContent(b);
        wxResource.setRemark("二维码:"+id+"的图形");
        wxResource.setFileType("jpg");
      ;
        WxQrCode wxQrCode=new WxQrCode();
        wxQrCode.setId(id);
        wxQrCode.setUrl(url);
        wxQrCode.setPictId(resourceId);
        wxResourceDao.save(wxResource);
        wxQrCodeDao.save(wxQrCode);

        System.out.println("\n  创建二维码成功！");

    }
    public void create500PermQrCode(){
        for(int i=0;i<500;i++)
            this.createPermQrCode();
    }
    public void create500QrCode(){
        for(int i=0;i<10;i++)
            try {
                this.createWxQrCode();
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
