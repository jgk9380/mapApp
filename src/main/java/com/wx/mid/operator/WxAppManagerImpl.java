package com.wx.mid.operator;

import java.math.BigDecimal;

import java.util.Date;

import java.util.List;
import java.util.Map;


import com.wx.mid.entity.*;
import com.wx.mid.WxBeanFactory;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.*;
import com.wx.mid.util.WxUtils;
import com.wx.mid.operator.bo.WxUserBo;
import com.wx.mid.operator.msg.MsgActionListener;
import net.sf.json.JSONObject;


import org.jboss.logging.Logger;
import org.springframework.jdbc.core.JdbcTemplate;


public class WxAppManagerImpl extends WxAppManagerBaseImpl {
    public WxAppManagerImpl(String appName) {
        super(appName);
    }

    WxPromotionDao wxPromotionDao = WxBeanFactoryImpl.getInstance().getBean("wxPromotionDao", WxPromotionDao.class);
    WxMailDao wxMailDao = WxBeanFactoryImpl.getInstance().getBean("wxMailDao", WxMailDao.class);
    WxMailArticleDao wxMailArticleDao =
        WxBeanFactoryImpl.getInstance().getBean("wxMailArticleDao", WxMailArticleDao.class);

    @Override
    public WxUser getWxUser(String openId) {
        WxUser res;
        //System.out.println("--appName=" + appName);
        WxApp wxApp = wxAppDao.findByAppName(appName);
        //Logger.getLogger(WxAppManagerImpl.class).info(operator);
        //System.out.println("wxappId=" + operator.getId() + "   openId " + openId);
        res = wxUserDao.findByAppIdAndOpenId(wxApp.getId(), openId);
        //res = wxUserDao.findByOpenId( openId);
        Date currentDate = new Date();
        if (res != null && res.getNickname() != null && res.getRefereshDate() != null) { //����Ϊ�ջ��ǳ�Ϊ�ջ��ϴθ���ʱ�䳬��һ��
            //System.out.println("res=" + res + "id=" + res.getId() + "nickName=" + res.getNickname());
            if ((currentDate.getTime() - res.getRefereshDate().getTime()) / 1000 / 3600 / 24 > 7) //����ʱ�䳬��һ����
                res = refreshWxUser(openId);
            return res;
        }
        res = refreshWxUser(openId);
        return res;
    }


    WxPerQrCodeDao wxPerQrCodeDao = WxBeanFactoryImpl.getInstance().getBean("wxPerQrCodeDao", WxPerQrCodeDao.class);
    JdbcTemplate jdbcTemplate = WxBeanFactoryImpl.getInstance().getJdbcTemplate();
    WxUserMsgDao wxUserMsgDao = WxBeanFactoryImpl.getInstance().getBean("wxUserMsgDao", WxUserMsgDao.class);
    WxMsgTypeDao wxMsgTypeDao = WxBeanFactoryImpl.getInstance().getBean("wxMsgTypeDao", WxMsgTypeDao.class);
    WxEvtTypeDao wxEvtTypeDao = WxBeanFactoryImpl.getInstance().getBean("wxEvtTypeDao", WxEvtTypeDao.class);

    @Override
    public WxUserMsg saveUserMsg(Map<String, String> argsMap) {
        WxUserMsg wue = new WxUserMsg();
        //int l = jdbcTemplate.queryForObject("select wx_seq_generator.nextval from dual", Integer.class);
        wue.setId(WxUtils.getSeqencesValue());

        WxMsgType wmt = wxMsgTypeDao.findById(argsMap.get("MsgType"));
        if (wmt == null) {
            Logger.getLogger(WxAppManagerImpl.class).error("û���ҵ��¼�����,���ʵ������������Ϊ:" +
                                                           argsMap.get("MsgType" + "����=" + argsMap.toString()));
            return null;
        }
        wue.setMsgType(wmt);
        //System.out.println("--args--:" + argsMap);
        if (argsMap.containsKey("Event")) {
            WxEventType wet1 = wxEvtTypeDao.findById(argsMap.get("Event").toString());
            wue.setEvtType(wet1);
        }
        wue.setEventKey(argsMap.get("EventKey"));
        wue.setMsgId(argsMap.get("MsgId"));
        wue.setWxUser(this.getWxUser(argsMap.get("FromUserName")));
        wue.setSceneArgs(JSONObject.fromObject(argsMap).toString());
        wue.setCreateTime(new Date());
        {
            Long subTime = Long.parseLong(argsMap.get("CreateTime")) * 1000;
            Date occurDate = new Date(subTime);
            wue.setOccurDate(occurDate);
        }
        WxUserMsg wum = wxUserMsgDao.save(wue); //��������������ֵ
        return wum;
    }


    @Override
    public void dispUserMsg(WxUserMsg wum) {
        WxBeanFactory wbfi = WxBeanFactoryImpl.getInstance();
        System.out.println("**********MsgActionListener.className=" + wum.getMsgType().getHandleClassName() +
                           " messageId=" + wum.getId());
        MsgActionListener mal = wbfi.getMsgActionListener(wum.getMsgType().getHandleClassName());
        mal.doAction(wum);

    }


    private static void refershAllUsersInfo() {
        WxAppManagerImpl wam = (WxAppManagerImpl) WxBeanFactoryImpl.getInstance().getWxAppManager("yctxq");
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        List<BigDecimal> wul = wxUserDao.findNoSubUsers("wxbf245420dadddff5"); //TODO
        System.out.println("һ��" + wul.size());
        int i = 0;
        for (BigDecimal useId : wul) {
            //System.out.println(useId);
            WxUser wx = wxUserDao.findById(useId.longValue());
            if (wx.getOpenId() != null) {
                WxUser rewxser = wam.refreshWxUser(wx.getOpenId());
                i++;
                if (wx.getNickname() != null && rewxser.getNickname() != null)
                    if (!wx.getNickname().equals(rewxser.getNickname()))
                        System.out.println("before.nickname=" + wx.getNickname() + "  after.nickName" +
                                           rewxser.getNickname() + "  " + (i));
            }
        }

    }

    @Override
    public WxOperator getOperator() {
        return wxOperator;
    }

    @Override
    public WxUserBo getWxUserBo(String openId) {
        WxUser wxUser;
        WxApp wx = wxAppDao.findByAppName(appName);
        // Logger.getLogger(WxAppManagerImpl.class).info(operator);
        //System.out.println("wxappId=" + operator.getId() + "   openId " + openId);
        wxUser = wxUserDao.findByAppIdAndOpenId(wx.getId(), openId);
        //res = wxUserDao.findByOpenId( openId);
        if (wxUser != null) {
            //System.out.println("res=" + res + "id=" + res.getId() + "nickName=" + res.getNickname());
            return WxBeanFactoryImpl.getInstance().getUserBo(wxUser);
        }
        wxUser = refreshWxUser(openId);
  return WxBeanFactoryImpl.getInstance().getUserBo(wxUser);

    }

    @Override
    public void sendWxMail(long receiveUserId, String cont, long sendUserId) {
        // TODO Implement this method
        WxMail wxMail = new WxMail();
        wxMail.setId(WxUtils.getSeqencesValue().longValue());
        wxMail.setUserId(receiveUserId);
        wxMail.setProdDate(new Date());
        wxMail.setContent(cont);
        if (sendUserId != 0)
            wxMail.setSendId(sendUserId);
        wxMailDao.save(wxMail);
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser wxUser = wxUserDao.findById(receiveUserId);
        WxBeanFactoryImpl.getInstance().getUserBo(wxUser).receiveMail(true);
    }


    @Override
    public void sendWxArticle(long userId, String title, String desc, String pictUrl, String url) {
        WxMailArticle wxMailArticle = new WxMailArticle();
        wxMailArticle.setId(WxUtils.getSeqencesValue().longValue());
        wxMailArticle.setUserId(userId);
        wxMailArticle.setProdDate(new Date());
        wxMailArticle.setTitle(title);
        wxMailArticle.setDesc(desc);
        wxMailArticle.setPictUrl(pictUrl);
        wxMailArticle.setUrl(url);
        wxMailArticleDao.save(wxMailArticle);
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser wxUser = wxUserDao.findById(userId);
        WxBeanFactoryImpl.getInstance().getUserBo(wxUser).receiveMailArticle(true);
    }

    public static void main(String[] args) {
        //refershAllUsersInfo();.
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        List<BigDecimal> userList = wxUserDao.find48Users();
        System.out.println(userList.size());
        int i=0;
        for (BigDecimal userId : userList) {
            WxBeanFactoryImpl.getInstance().getWxAppManager("yclt").sendWxMail(userId.longValue(),
                                                                                "盐城联通友情提醒：“刮刮卡活动目前暂告一段落，我们即将推出大转盘送流量，抢手机等活动，敬请期待!”",
                                                                                0);
        
            System.out.println(i++);
            
        } //WxBeanFactoryImpl.getInstance().getWxAppManager("yctxq").getWxUserBo("oEsXmwWQkf6V5KaLUMHCQHpC8F1E").receiveMail(false);
    }
}
