package com.wx.mid.operator.bo;

import java.awt.Image;
import java.awt.image.BufferedImage;

import java.util.Date;

import java.util.List;

import com.wx.mid.entity.*;
import com.wx.mid.entity.qrcode.WxQrCode;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.*;
import com.wx.mid.util.PictUtils;
import com.wx.mid.operator.PromotionGiftSelector;
import com.wx.mid.operator.WxAppManager;


import org.jboss.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;


public class WxUserBoImpl implements WxUserBo {
    WxUser wxUser;

    Account account;
    WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
    WxMailDao wxMailDao = WxBeanFactoryImpl.getInstance().getBean("wxMailDao", WxMailDao.class);
    WxMailArticleDao wxMailArticleDao =
        WxBeanFactoryImpl.getInstance().getBean("wxMailArticleDao", WxMailArticleDao.class);
    WxUserMsgDao wxUserMsgDao = WxBeanFactoryImpl.getInstance().getBean("wxUserMsgDao", WxUserMsgDao.class);
    JdbcTemplate jdbcTemplate = WxBeanFactoryImpl.getInstance().getJdbcTemplate();
    WxAppManager wxAppManager;

    public WxUserBoImpl(WxUser wxUser) { //�����ⲿ���ã�ͨ��spring�����ļ�ȡ��
        super();
        this.wxUser = wxUser;
        account =null;//= WxBeanFactoryImpl.getInstance().getAccount(wxUser.getId());
        WxAppDao wxAppDao = WxBeanFactoryImpl.getInstance().getBean("wxAppDao", WxAppDao.class);
        wxAppManager = WxBeanFactoryImpl.getInstance().getWxAppManager(wxUser.getWxApp().getAppName());
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public WxPromotionGift selectRandomGgkGift() { //��ȡ�����ƷID
        /**1��ȡ�����л
         * 2������ÿ���ȡ�����Ʒ����Ϊ�գ������ó�״̬Ϊ1��������
         * 3��ÿ�η���һ����Ʒ
         * */
        WxPromotionDao wxPromotionDao = WxBeanFactoryImpl.getInstance().getBean("wxPromotionDao", WxPromotionDao.class);
        List<WxPromotion> allProm = wxPromotionDao.findAll();
        WxPromotionGiftDao wxPromotionGiftDao =
            WxBeanFactoryImpl.getInstance().getBean("wxPromotionGiftDao", WxPromotionGiftDao.class);
        for (WxPromotion wp : allProm) {
            PromotionGiftSelector pgs =
                WxBeanFactoryImpl.getInstance().getPromotionGiftSelector(wp.getHandleClassName());
            //�޸�gift��״̬Ϊ1��Ԥռ��getPromotionGiftSelector�н���
            WxPromotionGift wpg = pgs.getGiftId(wxUser.getId(), wp.getId());
            if (wpg != null) {
                return wpg;
            }
        }
        return null;
    }


    @Override
    public int getRefereeCount() {
        return wxUserDao.getRefereeCount(wxUser);
    }

    @Override
    public String getShareImageMediaId() {
        WxQrCodeProxyImpl wqcp = new WxQrCodeProxyImpl();
        return wqcp.getShareImageMediaId(this.wxUser.getId());
    }

    @Override
    public String getQrCodeUrl() {
        // TODO Implement this method
        return this.getUserQrCode(wxUser.getId()).getPictureUrl();
    }

    @Override
    public boolean bindTele(String tele) {
        WxUser targetWxUser = wxUserDao.findByTeleAndAppId(tele, wxUser.getWxApp().getId());
        if (targetWxUser != null) {
            Logger.getLogger(WxUserBoImpl.class).error("�����Ѵ���");
            return false;
        }
        wxUser.setTele(tele);
        wxUserDao.save(wxUser);
        return true;
    }


    @Override
    public String getTele() {
        return wxUser.getTele();
    }

    @Override
    public boolean addUserData(String userName, String address, String promId) {
        wxUser.setLongName(userName);
        wxUser.setMailAddr(address);
        wxUser.setPromotionCode(promId);
        wxUserDao.save(wxUser);
        return true;
    }

    public static void main(String[] args) {
        //WxUserBo wub = new WxUserBoImpl(3007095);

        //        WxUserBoImpl wub = (WxUserBoImpl) WxBeanFactoryImpl.getInstance().getUserBo(276507);
        //        wub.getQrCode();
        BufferedImage shareImage = null;
        {
            PictUtils pictUtil = new PictUtils();
            BufferedImage sourceImage = pictUtil.loadImageLocal("D:\\image\\ssv3.jpg");
            BufferedImage qrImage = pictUtil.loadImageLocal("D:\\image\\wr.jpg");
            pictUtil.modifyImage(sourceImage, "" + "123456" + "", 500, 675);
            Image sizeQrImage = qrImage.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
            BufferedImage bufferedQrImage = pictUtil.convertImageToBuffer(sizeQrImage);
            shareImage = pictUtil.modifyImagetogeter(bufferedQrImage, sourceImage, 115, 625);
            pictUtil.writeImageLocal("D:\\image\\res.jpg", shareImage);
        }


    }


    public void setAccount(Account account) {
        this.account = account;
    }

    private WxQrCode getUserQrCode(long userId) {
        WxQrCodeProxyImpl wqpi=new WxQrCodeProxyImpl();
        return wqpi.getUserQrCode(userId);
        
    }

    @Override
    public WxAppManager getWxAppManager() { //TODO ΪʲôҪ��
        return this.wxAppManager;
    }


    @Override
    public byte[] getQrCode() {
        return this.getUserQrCode(wxUser.getId()).getWxMedia().getContent();
    }

    @Override
    public void receiveMail(boolean check) {
        if (check)
            if (wxUserMsgDao.get24HourMsgByUserId(wxUser.getId()) == 0)
                return;
        List<WxMail> wxMailList = wxMailDao.findNoSendedMailByUserId(wxUser.getId());
        //TODO if24Сʱ��½
        if (wxMailList.size() == 0)
            return;
        for (WxMail wm : wxMailList) {
            wm.setIsSend(true);
            wm.setSendDate(new Date());

            String content = null; //=wm.getContent();
            if (wm.getSendId() == null) {
                content = "系统留言：" + wm.getContent();
            } else {
                WxUser sendUser = wxUserDao.findById(wm.getSendId());
                content = "你的朋友：\'" + sendUser.getNickname() + "\'给你留言：" + wm.getContent();
            }
            this.getWxAppManager().getOperator().sendTxtMessage(wxUser.getOpenId(), content);
        }
        wxMailDao.save(wxMailList);
    }

    @Override
    public void receiveMailArticle(boolean check) {

    }

    @Override
    public String[] getSpeedInfo() {

return null;
    }

    @Override
    public WxUser getWxUser() {
        return wxUser;
    }
}
