package com.wx.mid.operator.bo;

import java.awt.image.BufferedImage;

import java.math.BigDecimal;

import java.util.Date;

import com.wx.mid.base.pojo.WeixinMedia;
import com.wx.mid.base.pojo.WeixinQRCode;
import com.wx.mid.entity.WxUser;
import com.wx.mid.entity.media.WxMedia;
import com.wx.mid.entity.qrcode.WxPermQrCode;
import com.wx.mid.entity.qrcode.WxQrCode;
import com.wx.mid.entity.qrcode.WxTempQrCode;
import com.wx.enump.QrActionName;
import com.wx.enump.UserType;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAssertDao;
import com.wx.mid.dao.WxQrCodeDao;
import com.wx.mid.dao.WxUserDao;
import com.wx.mid.util.WxPictTool;
import com.wx.mid.util.WxUtils;
import com.wx.mid.operator.WxAppManager;
import com.wx.mid.operator.WxAssert;
import net.sf.json.JSONObject;





public class WxQrCodeProxyImpl implements WxQrCodeProxy {
    public WxQrCodeProxyImpl() {
        super();
    }
    WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);

    @Override
    public WxQrCode getUserQrCode(long userId) {
        //TODO 区分用户类型  员工，代理等
        WxUser wxUser = wxUserDao.findById(userId);
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        WxQrCode wqc = wxQrCodeDao.findByUserId(userId);

        if (wqc == null) {
            if (wxUser.getUserType() == UserType.employee)
                wqc = this.createPermUserQrCode(userId);
            else {
                wqc = this.createTempUserQrCode(userId);
            }
            return wqc;
        }
        System.out.println("valid:" + wqc.isValid());
        if (!wqc.isValid()) {
            wxQrCodeDao.delete(wqc);
            wqc = this.createTempUserQrCode(userId);
            return wqc;
        }
        return wqc;
    }

    @Override
    public WxQrCode getCouponQrCode(long couponId) {
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        WxQrCode wqc = wxQrCodeDao.findByConponId(couponId);
        if (wqc == null)
            return createTempCouponQrCode(couponId);
        if (!wqc.isValid()) {
            wxQrCodeDao.delete(wqc);
            return createTempCouponQrCode(couponId);
        }
        return wqc;
    }


    private WxTempQrCode createTempUserQrCode(long userId) {
        WxUser wxUser = wxUserDao.findById(userId);
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        int l = WxUtils.getScenenIdSeqencesValue();
        WxAppManager wxAppManager = WxBeanFactoryImpl.getInstance().getWxAppManager(wxUser.getWxApp().getAppName());
        WeixinQRCode wxqc = wxAppManager.getOperator().createTemporaryQRCode(l, 3600 * 30 * 24);
        WxTempQrCode wqc = new WxTempQrCode();
        wqc.setTicket(wxqc.getTicket());
        wqc.setPictureUrl(wxqc.getUrl());
        wqc.setWxMedia(this.getWxQrCodePicture(wxUser.getWxApp().getAppName(), wxqc.getTicket()));
        wqc.setDestType("user");
        wqc.setDestId(new BigDecimal(userId));
        wqc.setCreateDate(new Date());
        wqc.setActionName(QrActionName.QR_SCENE);
        wqc.setSencenId(new BigDecimal(l));
        wqc.setExpireSeconds(wxqc.getExpireSeconds());
        wqc = wxQrCodeDao.save(wqc);
        return wqc;
    }

    private WxPermQrCode createPermUserQrCode(long userId) {
        WxUser wxUser = wxUserDao.findById(userId);
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        int l = WxUtils.getScenenIdSeqencesValue();
        WxAppManager wxAppManager = WxBeanFactoryImpl.getInstance().getWxAppManager(wxUser.getWxApp().getAppName());
        JSONObject json = wxAppManager.getOperator().createPermanentQRCode(l);
        WxPermQrCode wqc = new WxPermQrCode();
        wqc.setTicket(json.getString("ticket"));
        wqc.setPictureUrl(json.getString("url"));
        wqc.setWxMedia(this.getWxQrCodePicture(wxUser.getWxApp().getAppName(), json.getString("ticket")));
        wqc.setDestType("user");
        wqc.setDestId(new BigDecimal(userId));
        wqc.setCreateDate(new Date());
        wqc.setActionName(QrActionName.QR_LIMIT_SCENE);
        wqc.setSencenId(new BigDecimal(l));
        wqc = wxQrCodeDao.save(wqc);
        return wqc;
    }


    private WxTempQrCode createTempCouponQrCode(long couponId) {
        WxAssertDao wxAssertDao = WxBeanFactoryImpl.getInstance().getBean("wxAssertDao", WxAssertDao.class);
        WxAssert wxAssert = wxAssertDao.findCouponById(couponId);
        WxUser wxUser = wxUserDao.findById(wxAssert.getWxUser().getId());
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        int l = WxUtils.getScenenIdSeqencesValue();
        WxAppManager wxAppManager = WxBeanFactoryImpl.getInstance().getWxAppManager(wxUser.getWxApp().getAppName());
        WeixinQRCode wxqc = wxAppManager.getOperator().createTemporaryQRCode(l, 3600 * 30 * 24);
        WxTempQrCode wqc = new WxTempQrCode();
        wqc.setTicket(wxqc.getTicket());
        wqc.setPictureUrl(wxqc.getUrl());
        wqc.setWxMedia(this.getWxQrCodePicture(wxUser.getWxApp().getAppName(), wxqc.getTicket()));
        wqc.setDestType("coupon");
        wqc.setDestId(new BigDecimal(couponId));
        wqc.setCreateDate(new Date());
        wqc.setActionName(QrActionName.QR_SCENE);
        wqc.setSencenId(new BigDecimal(l));
        wqc.setExpireSeconds(wxqc.getExpireSeconds());
        wqc = wxQrCodeDao.save(wqc);
        return wqc;
    }

    private WxMedia getWxQrCodePicture(String appName, String ticket) {
        WxMedia wxMedia = new WxMedia();
        wxMedia.setId(WxUtils.getSeqencesValue());
        wxMedia.setCreateDate(new Date());
        wxMedia.setType("image");
        wxMedia.setSuffix("jpg");
        wxMedia.setRemark("二维码图片");
        wxMedia.setContent(WxBeanFactoryImpl.getInstance().getWxAppManager(appName).getOperator().getQRCodeImage(ticket));
        return wxMedia;
    }


    public String getShareImageMediaId(long userId) {
        WxUser wxUser = wxUserDao.findById(userId);
        WxAppManager wxAppManager = WxBeanFactoryImpl.getInstance().getWxAppManager(wxUser.getWxApp().getAppName());
        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
        WxQrCode qrCode = this.getUserQrCode(userId);

        String mediaId = qrCode.getShareImageMediaId();
        Date currentDate = new Date();
        Date upDate = qrCode.getShareImageMediaUptime();
        if (upDate == null)
            upDate = new Date(1); //ȡһ���ܾ�ǰ��ʱ��
        long hours = (currentDate.getTime() - upDate.getTime()) / 1000 / 3600;
        if (mediaId == null || hours > 36) {
            BufferedImage shareImage = null;
            {
                WxPictTool wxPictUtil = WxBeanFactoryImpl.getInstance().getWxPictTool();
                //                BufferedImage sourceImage = pictUtil.loadImageLocal("D:\\image\\sbv2.jpg");
                //                BufferedImage qrImage = pictUtil.convertByteArrayToImage(qrCode.getWxMedia().getContent());
                //                pictUtil.modifyImage(sourceImage, "" + wxUser.getNickname() + "", 505, 675);
                //                Image sizeQrImage = qrImage.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
                //                BufferedImage bufferedQrImage = pictUtil.convertImageToBuffer(sizeQrImage);
                //                shareImage = pictUtil.modifyImagetogeter(bufferedQrImage, sourceImage, 115, 625);
                String nn = wxUser.getNickname();
                byte[] qrCodePic = qrCode.getWxMedia().getContent();
                BufferedImage qrImage = wxPictUtil.convertByteArrayToImage(qrCodePic);             
                shareImage = wxPictUtil.getSpreadPosters( nn, qrImage);

            }
            WeixinMedia wn = wxAppManager.getOperator().uploadMedia("image", shareImage);
            qrCode.setShareImageMediaId(wn.getMediaId());
            qrCode.setShareImageMediaUptime(new Date());
            wxQrCodeDao.save(qrCode);
            mediaId = wn.getMediaId();
        }
        return mediaId;
    }

    public static void main(String[] args) {
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser wxUser = wxUserDao.findById(8335201);
        WxQrCodeProxyImpl wqcpi = new WxQrCodeProxyImpl();
        wqcpi.getUserQrCode(8335201);
        String mediaId = wqcpi.getShareImageMediaId(8335201);
        System.out.println("mediaId=" + mediaId);
        wqcpi.getCouponQrCode(-101);
    }

}
