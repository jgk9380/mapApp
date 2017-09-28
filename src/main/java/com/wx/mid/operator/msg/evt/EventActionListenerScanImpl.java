package com.wx.mid.operator.msg.evt;

import com.wx.mid.entity.WxUserMsg;


import org.springframework.stereotype.Component;



@Component("scanEvtActionListenBaseImpl")

public class EventActionListenerScanImpl implements EventActionListener {
    public EventActionListenerScanImpl() {
        super();
    }


    /**
     * if(coupon)
     *  if(check(scaner))
     *    update(coupon)
     * if(queue)//
     *   queue(scaner)
     */

    @Override
    public void doAction(WxUserMsg wxMsg) {
//        String appName = wxMsg.getWxUser().getWxApp().getAppName();
//        WxAppManager wxApp = WxBeanFactoryImpl.getInstance().getWxAppManager(appName);
//        System.out.println("######acton scan");
//        System.out.println(wxMsg.getSceneArgs());
//        JSONObject json = JSONObject.fromObject(wxMsg.getSceneArgs());
//        String ticket = json.getString("Ticket");
//        WxQrCodeDao wxQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxQrCodeDao", WxQrCodeDao.class);
//        WxAssertDao wxAssertDao = WxBeanFactoryImpl.getInstance().getBean("wxAssertDao", WxAssertDao.class);
//        WxQrCode wxQrCode = wxQrCodeDao.findByTicket(ticket);
//        if (wxQrCode.getDestType().equals("coupon")) {
//            WxUser scaner=wxMsg.getWxUser();
//            System.out.println("scaner.nickName="+scaner.getNickname());
//            //TODO 检查scaner是否有权限
//            WxAssert wxCoupon = wxAssertDao.findById(wxQrCode.getDestId());
//            if(wxCoupon.isUsed()){
//                wxApp.sendWxMail(scaner.getId(), "该优惠券已使用，不能重复使用："+wxCoupon.getAssertType().getAlias()+",面值："+wxCoupon.getFaceValue()+"元。", 0);
//                return;
//            }
//            wxCoupon.setUsed(true);
//            wxAssertDao.save(wxCoupon);
//            //发送消息给用户
//
//
//            wxApp.sendWxMail(wxCoupon.getWxUser().getId(), "已成功使用:"+wxCoupon.getAssertType().getAlias()+",面值："+wxCoupon.getFaceValue()+"元。", 0);
//            wxApp.sendWxMail(scaner.getId(), "已成功受理:"+wxCoupon.getAssertType().getAlias()+",面值："+wxCoupon.getFaceValue()+"元。", 0);
//            //返回消息给应用，更新界面
//        }
//
//        if (wxQrCode.getDestType().equals("queue")) {
//
//        }

    }
}
