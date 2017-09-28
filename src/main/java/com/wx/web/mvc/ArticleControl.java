package com.wx.web.mvc;

import java.io.UnsupportedEncodingException;

import java.math.BigDecimal;

import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.base.pojo.WeixinOauth2Token;
import com.wx.mid.base.util.JSSDKSignUtils;
import com.wx.mid.dao.*;
import com.wx.mid.entity.*;
import com.wx.mid.operator.bo.WxUserBo;
import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/article")
public class ArticleControl {
    static Map<String, String> codeDeposit = new HashMap<>();//用于Code 与openId的对应关系缓存

    public ArticleControl() {
        super();
    }

    @RequestMapping(value = "/articleInfo/{sender}/{artId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getArticleInfo(@PathVariable("artId") BigDecimal artId, @PathVariable("sender") Long userId) {

        WxArticleDao wxArticleDao = WxBeanFactoryImpl.getInstance().getBean("wxArticleDao", WxArticleDao.class);
        WxArticle art = wxArticleDao.findById(artId);
        JSONObject result = JSONObject.fromObject(art);

        WxUserDao wxUsereDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser wxUser = wxUsereDao.findById(userId);
        result.put("unitTitle", wxUser.getWxApp().getRemark());
        result.put("unitTitleUrl", wxUser.getWxApp().getWelcomeUrl());
        result.put("senderName", wxUser.getNickname());
        String shareLink = "http://www.ycunicom.com/wx3/artContainer.html?sender=uid&artId=aid";
        result.put("shareLink", shareLink.replace("aid", artId.toString()).replace("uid", userId.toString()));
        return result;
    }


    @RequestMapping(value = "/logShareArticle/{shareType}/{sender}/{sharer}/{artId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject shareArticle(@PathVariable("shareType") String shareType, @PathVariable("sharer") long sharer,
                                   @PathVariable("sender") Long sender, @PathVariable("artId") BigDecimal artId) {
        System.out.println("share sharer=" + sharer);
        System.out.println("share shareType=" + shareType);
        System.out.println("share sender=" + sender);
        System.out.println("share artId=" + artId);
        WxShareLogDao wxShareLogDao = WxBeanFactoryImpl.getInstance().getBean("wxShareLogDao", WxShareLogDao.class);
        WxShareLog wxShareLog = new WxShareLog();
        wxShareLog.setId(WxUtils.getSeqencesValue().longValue());
        wxShareLog.setUserId(sharer);
        wxShareLog.setRemark(sender.toString());
        wxShareLog.setScence(artId.toString());
        wxShareLog.setShareFlag("article");
        wxShareLog.setShareType(shareType); //1，朋友圈，2，朋友
        wxShareLogDao.save(wxShareLog);
        JSONObject res = new JSONObject();
        res.put("result", "sucess");
        return res;
    }

    @RequestMapping(value = "/wxJsSdkConfig", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject articleWxJsSdkConfig(@RequestParam("url") String url, @RequestParam("sender") long sender) {
        System.out.println("in wxJsSdkConfig url=" + url);
        System.out.println("in wxJsSdkConfig userId=" + sender);
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser wxUser = wxUserDao.findById(sender);
        WxUserBo wxUserBo = WxBeanFactoryImpl.getInstance().getUserBo(wxUser);
        Map<String, String> map = JSSDKSignUtils.sign(wxUserBo.getWxAppManager().getOperator().getJsApiTicket().getTicket(), url);
        JSONObject res = JSONObject.fromObject(map);
        res.put("appId", wxUserBo.getWxUser().getWxApp().getId());
        System.out.println("in wxJsSdkConfig return=" + res.toString());
        return res;
    }

   

    @RequestMapping(value = "/readerByCode/{sender}/{code}/{artId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getReaderByCode(@PathVariable("code") String code, @PathVariable("sender") long senderId,
                                      @PathVariable("artId") BigDecimal artId) {
        String openId = null;
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser sendWxUser = wxUserDao.findById(senderId);
        if (codeDeposit.containsKey(code)) {
            openId = codeDeposit.get(code);
        } else {           
            if (!"authdeny".equals(code) && code != null) {
                WeixinOauth2Token weixinOauth2Token =
                    WxBeanFactoryImpl.getInstance().getWxAppManager(sendWxUser.getWxApp().getAppName()).getOperator().getOauth2AccessToken(code);
                if (weixinOauth2Token != null) {
                    openId = weixinOauth2Token.getOpenId();
                    codeDeposit.put(code,openId);
                }
            }
        }
        JSONObject res = new JSONObject();
        if (openId == null) {
            return res;
        }
        System.out.println("openId=" + openId);
        { //log read
            WxUser readWxUser = WxBeanFactoryImpl.getInstance().getWxAppManager(sendWxUser.getWxApp().getAppName()).getWxUser(openId);
            res.put("readerId", readWxUser.getId());
            WxArticleReadHistoryDao wxArticleReadHistoryDao =
                WxBeanFactoryImpl.getInstance().getBean("wxArticleReadHistoryDao", WxArticleReadHistoryDao.class);
            WxArticleReadHistory wxArticleReadHistory = new WxArticleReadHistory();
            wxArticleReadHistory.setId(WxUtils.getSeqencesValue());
            wxArticleReadHistory.setReadDate(new Date());
            wxArticleReadHistory.setUserId(readWxUser.getId());
            wxArticleReadHistory.setSender(senderId);
            wxArticleReadHistory.setArtId(artId);
            wxArticleReadHistoryDao.save(wxArticleReadHistory);
            //System.out.println("WxConfig=" + res.toString());
        }
        return res;
    }

    @RequestMapping(value = "/shareConnfig/{sender}/{reader}/{artId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getShareConnfig(@PathVariable("sender") long senderId, @PathVariable("reader") long readerId,
                                      @PathVariable("artId") BigDecimal artId) {
        System.out.println("in shareconfig readerId=" + readerId);
        System.out.println("in shareconfig senderId=" + senderId);
        System.out.println("in shareconfig artId=" + artId);
        JSONObject result = getArticleInfo(artId, senderId);
        String urlTempalate =
            "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=APPID&redirect_uri=URI&response_type=RES_TYPE" +
            "&scope=SCOPE&state=STATE#wechat_redirect";

        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        WxUser shareUser = wxUserDao.findById(readerId);
        WxUser sendUser = wxUserDao.findById(senderId);
        if (shareUser == null || shareUser.getSubscribeDate() == null) //如果是从未关注用户
            shareUser = sendUser;
        String shareLink =
            "http://www.ycunicom.com/wx3/artContainer.html?sender=uid&artId=aid".replace("aid", artId.toString()).replace("uid",
                                                                                                                          shareUser.getId().toString());
        try {
            shareLink = URLEncoder.encode(shareLink, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = urlTempalate.replace("URI", shareLink);
        url = url.replace("APPID", shareUser.getWxApp().getId());
        //url = url.replace("URI", wvm.getBaseAddr() + wvm.getTargetUrl());
        url = url.replace("RES_TYPE", "code");
        url = url.replace("SCOPE", "snsapi_base");
        url = url.replace("STATE", shareUser.getId().toString());
        result.put("shareLink", url);
        return result;
    }


    @RequestMapping(value = "/submitDiscuss/{sender}/{reader}/{artId}/{content}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject submitDiscuss(@PathVariable("sender") long senderId, @PathVariable("reader") long readerId,
                                    @PathVariable("artId") BigDecimal artId, @PathVariable("content") String content) {
        System.out.println("in submitDiscuss readerId=" + readerId);
        System.out.println("in submitDiscuss senderId=" + senderId);
        System.out.println("in submitDiscuss artId=" + artId);
        System.out.println("in submitDiscuss content=" + content);
        WxArticleDiscuss wad = new WxArticleDiscuss();
        wad.setArtId(artId);
        wad.setContent(content);
        wad.setDiscussDate(new Date());
        wad.setUserId(readerId);
        wad.setId(WxUtils.getSeqencesValue());
        WxArticleDiscussDao wxArticleDiscussDao = WxBeanFactoryImpl.getInstance().getBean("wxArticleDiscussDao", WxArticleDiscussDao.class);
        wxArticleDiscussDao.save(wad);
        JSONObject result = new JSONObject();
        result.put("result", "sucess");
        return result;
    }

    @RequestMapping(value = "/DiscussList/{artId}/{pageNumber}", method = RequestMethod.GET) //应该支持刷新，下拉增加一页显示内容
    @ResponseBody
    public JSONObject getDiscussList(@PathVariable("artId") BigDecimal artId, @PathVariable("pageNumber") int pageNumber) {
        System.out.println("getdiscuss pageNumber=" + pageNumber);
        JSONObject res = new JSONObject();
        WxArticleDiscussDao wadDao = WxBeanFactoryImpl.getInstance().getBean("wxArticleDiscussDao", WxArticleDiscussDao.class);
        WxUserDao wxUserDao = WxBeanFactoryImpl.getInstance().getBean("wxUserDao", WxUserDao.class);
        Pageable pageAble = new PageRequest(pageNumber, 10, new Sort(Sort.Direction.DESC, "discussDate"));
        List<WxArticleDiscuss> list = wadDao.findByArtId(pageAble, artId);
        List<JSONObject> jsonList = new ArrayList<>();
        for (WxArticleDiscuss wad : list) {
            JSONObject wadJSON = JSONObject.fromObject(wad);
            wadJSON.put("nickName", wxUserDao.findById(wad.getUserId()).getNickname());
            jsonList.add(wadJSON);
        }
        res.put("total", wadDao.findCountByArtId(artId));
        res.put("discussList", jsonList);
        System.out.println("list.size=" + list.size());
        return res;
    }


}
