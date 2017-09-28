package com.wx.mid.operator.msg.evt.menu;

import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wx.mid.base.message.resp.Article;
import com.wx.mid.entity.WxArticle;
import com.wx.mid.entity.WxArticleSendHistory;
import com.wx.mid.entity.WxUser;
import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxArticleDao;
import com.wx.mid.dao.WxArticleSendHistoryDao;
import com.wx.mid.operator.WxAppManager;
import net.sf.json.JSONObject;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;



@Component("menuActionListenerJRTTImpl")
//今日头条,
//按发送日期查询,如果没有可发送记录，发送最近的前三条。
public class MenuActionListenerJRTTImpl implements MenuActionListener {
    public MenuActionListenerJRTTImpl() {
        super();
    }

    @Override
    public void doAction(WxUserMsg wum) {
        String scenenArgs = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(scenenArgs);
        String receiver = (String) json.get("ToUserName");
        String sender = (String) json.get("FromUserName");
        String eventKey = json.getString("EventKey");
        System.out.println("appName=" + wum.getWxUser().getWxApp().getAppName());
        System.out.println("toUserOpenId=" + receiver);
        WxAppManager wam = WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        //wam.getOperator().sendTxtMessage(sender, "今日头条测试");
        List<Article> artList = getArticleList(wum.getWxUser());
        wam.getOperator().sendArticlesMessage(sender, artList);
    }

    private List<Article> getArticleList(WxUser wxUser) {
        //TODO
        Pageable pageable = new PageRequest(0, 3);
        String baseUrl = "http://www.ycunicom.com/wx3/artContainer.html?sender=uid&artId=aid";
       
        String urlTempalate =
            "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=APPID&redirect_uri=URI&response_type=RES_TYPE" + "&scope=SCOPE&state=STATE#wechat_redirect";
        List<Article> artList = new ArrayList<>();
        WxArticleDao wxArticleDao = WxBeanFactoryImpl.getInstance().getBean("wxArticleDao", WxArticleDao.class);
        List<WxArticle> wqc = wxArticleDao.getNews(pageable, wxUser.getId(), new Date());
        if (wqc.size() == 0) {
            System.out.println("getnearly()");
            wqc = wxArticleDao.getNearlyNews(pageable, new Date());

        }
        for (WxArticle wa : wqc) {
            Article art = new Article();
            String tempBaseUrl = baseUrl.replace("uid", wxUser.getId().toString()).replace("aid", wa.getId().toString());
            try {
                tempBaseUrl = URLEncoder.encode(tempBaseUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url=urlTempalate.replace("URI", tempBaseUrl);
            url = url.replace("APPID", wxUser.getWxApp().getId());
            //url = url.replace("URI", wvm.getBaseAddr() + wvm.getTargetUrl());
            url = url.replace("RES_TYPE", "code" );
            url = url.replace("SCOPE", "snsapi_base" );
            url = url.replace("STATE", wxUser.getId().toString());
            art.setUrl(url);
            art.setPicUrl(wa.getPicUrl());
            art.setTitle(wa.getTitle());
            art.setDescription(wa.getArticleDesc());
            artList.add(art);
        }
        WxArticleSendHistoryDao wasd =
            WxBeanFactoryImpl.getInstance().getBean("wxArticleSendHistoryDao", WxArticleSendHistoryDao.class);
        for (WxArticle wa : wqc) {
            WxArticleSendHistory wsh = new WxArticleSendHistory();
            wsh.setArtId(wa.getId());
            wsh.setUserId(new BigDecimal(wxUser.getId()));
            wsh.setSendDate(new Date());
            wasd.save(wsh);
        }
        return artList;
    }

}
