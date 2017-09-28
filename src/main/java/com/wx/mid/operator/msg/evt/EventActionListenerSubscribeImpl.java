package com.wx.mid.operator.msg.evt;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import com.wx.mid.base.message.resp.Article;
import com.wx.mid.entity.WxPerQrCode;
import com.wx.mid.entity.WxUser;
import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.*;
import com.wx.mid.operator.WxAppManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;




@Component("subscribeEvtActionListenBaseImpl")

public class EventActionListenerSubscribeImpl implements EventActionListener {
    //    final String url = "http://www.ycunicom.com/wx2/welcome/wel.html";
    //    final String title = "盐城联通欢迎你！";
    //    final String pictUrl = "http://www.ycunicom.com/wx2/welcome/wel1.jpg";
    //    final String desc = "感谢你关注盐城联通，关注送好礼，每天有好礼，推荐好友更送好礼";

    public EventActionListenerSubscribeImpl() {
        super();
    }
    @Autowired
    WxUserMsgDao wxUserMsgDao;
    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxAppDao wxAppDao;
    @Autowired
    WxPerQrCodeDao wxPerQrCodeDao;
    @Autowired
    WxMsgTypeDao wxMsgTypeDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void doAction(WxUserMsg wum) {
        System.out.println("######acton subs");
        wum.getWxUser().setSubscribeStatus(1);
        wum.getWxUser().setSubscribeDate(new Date());
        //发送推荐信息
        sendRefereeInfo(wum);
        //发送欢迎图文
        sendWelcomeArticle(wum);
        wxUserDao.save(wum.getWxUser());
        wxUserMsgDao.save(wum);
    }


    private void sendWelcomeArticle(WxUserMsg wum) {
        //发送欢迎图文
        WxAppManager wam = WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        String url = wum.getWxUser().getWxApp().getWelcomeUrl();
        String title = wum.getWxUser().getWxApp().getWelcomeTitle();
        String pictUrl = wum.getWxUser().getWxApp().getWelcomePictUrl();
        String desc = wum.getWxUser().getWxApp().getWelcomeDesc();

        Article art = new Article();
        art.setTitle(title);
        art.setUrl(url);
        art.setPicUrl(pictUrl);
        art.setDescription(desc);
        List<Article> artl = new ArrayList<>();
        artl.add(art);
        boolean xmlString1 = wam.getOperator().sendArticlesMessage(wum.getWxUser().getOpenId(), artl);

        return;
    }

    private void sendRefereeInfo(WxUserMsg wum) {
        WxAppManager wam = WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        int oldStatus = wum.getWxUser().getSubscribeStatus();

        String eventKey = wum.getEventKey();
        if (eventKey != null && oldStatus != -1 && eventKey.length() > 8) {
            //TODO推荐人提醒
            String scenenId = eventKey.substring(8, eventKey.length());
            WxPerQrCode pqc = wxPerQrCodeDao.findBySencenId(new BigDecimal(scenenId));
            if (pqc != null) {
                WxUser referee = wxUserDao.findById(pqc.getUserId());
                wum.getWxUser().setReferee(referee);

                int refereeCount = wxUserDao.getRefereeCount(referee) + 1;
                int refereeCanelCount = wxUserDao.getRefereeCacelCount(referee);
                String mailCont;
                String refInfoTail = "，继续努力，大奖等着你！";
                String noCancelRefereeTemp = "你推荐的用户：【nn】关注了盐城联通，目前共成功推荐了 rc 户" + refInfoTail;
                String cancelRefereeTemp = noCancelRefereeTemp + ",其中 cc 户已取消关注，净推荐 rf 户" + refInfoTail;
                if (refereeCanelCount == 0) {
                    mailCont =noCancelRefereeTemp.replace("nn", wum.getWxUser().getNickname());
                    mailCont=mailCont.replace("rc", ""+refereeCount);
                       // "你推荐的用户：【" + wum.getWxUser().getNickname() + "】关注了盐城联通，目前共成功推荐了" + refereeCount +  "户，继续努力，大奖等着你！";
                } else {
                    mailCont =noCancelRefereeTemp.replace("nn", wum.getWxUser().getNickname());
                    mailCont=mailCont.replace("rc", ""+refereeCount);
                    mailCont=mailCont.replace("cc", ""+(refereeCount + refereeCanelCount));
                    mailCont=mailCont.replace("rf", ""+refereeCount);
                       // "你推荐的用户：【" + wum.getWxUser().getNickname() + "】关注了盐城联通，目前共成功推荐了" + (refereeCount + refereeCanelCount) + "户,
                       // 其中" + refereeCanelCount + "户已取消关注，净推荐" + refereeCount + "户，继续努力，大奖等着你！";
                }
                wam.sendWxMail(referee.getId(), mailCont, 0);
            }
        }
    }

    public static void main(String[] args) {
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        List<BigDecimal> idList = wxUserDao.findRefereeList();
        System.out.println(idList.size());
        int i = 0;
        for (BigDecimal id : idList) {
            WxUser referee = wxUserDao.findById(id.longValue());
            int refereeCount = wxUserDao.getRefereeCount(referee);
            int refereeCanelCount = wxUserDao.getRefereeCacelCount(referee);
            String mailCont;
            String uname = wxUserDao.findLastRefereeName(id.longValue());
            if (refereeCanelCount == 0)
                mailCont = "你推荐的用户：【" + uname + "】关注了盐城联通，目前共成功推荐了" + refereeCount + "户，继续努力，大奖等着你！";
            else
                mailCont =
                    "你推荐的用户：【" + uname + "】关注了盐城联通，目前共成功推荐了" + (refereeCount + refereeCanelCount) + "户,其中" +
                    refereeCanelCount + "户已取消关注，净推荐" + refereeCount + "户，继续努力，大奖等着你！";
            if (refereeCount > 0) {
                WxBeanFactoryImpl.getInstance().getWxAppManager(referee.getWxApp().getAppName()).sendWxMail(referee.getId(),
                                                                                                            mailCont,
                                                                                                            0);


            }


        }
    }
}
