package com.wx.mid.operator.bo;


import com.wx.mid.entity.WxPromotionGift;
import com.wx.mid.entity.WxUser;
import com.wx.mid.operator.WxAppManager;


public interface WxUserBo {
    WxAppManager getWxAppManager();
    Account getAccount();
    WxPromotionGift selectRandomGgkGift(); //ѡȡ�����Ʒ
    int getRefereeCount();
    String  getShareImageMediaId();
    String  getQrCodeUrl();
    boolean bindTele(String tele);
    String getTele();    
    boolean addUserData(String userName, String address, String promId);//�ύ�û�����
    byte[] getQrCode();    
    void receiveMail(boolean check);
    void receiveMailArticle(boolean check);
    String[] getSpeedInfo();
    WxUser getWxUser();
}
