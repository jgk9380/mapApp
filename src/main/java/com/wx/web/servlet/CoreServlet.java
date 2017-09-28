package com.wx.web.servlet;


import com.wx.mid.WxBeanFactory;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.base.util.MessageUtil;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.operator.WxAppManager;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;



@WebServlet(name = "CoreServlet", urlPatterns = { "/coreServlet" })
public class CoreServlet extends HttpServlet {
    @SuppressWarnings("compatibility:-5963779121706528509")
    private static final long serialVersionUID = 4440739483644821986L;

    /**
     * ����У�飨ȷ����������΢�ŷ�������
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.getLogger(CoreServlet.class).info("doGet");

        // ΢�ż���ǩ��
        String signature = request.getParameter("signature");
        //Logger.getLogger(CoreServlet.class).info("signature=" + signature);
        // ʱ���
        String timestamp = request.getParameter("timestamp");
        //Logger.getLogger(CoreServlet.class).info("timestamp=" + timestamp);
        // �����
        String nonce = request.getParameter("nonce");
        //Logger.getLogger(CoreServlet.class).info("nonce=" + nonce);
        // ����ַ���
        String echostr = request.getParameter("echostr");
        //Logger.getLogger(CoreServlet.class).info("echostr=" + echostr);
        PrintWriter out = response.getWriter();
        WxBeanFactory wfi = WxBeanFactoryImpl.getInstance();
        WxAppManager wam = wfi.getWxAppManager("yctxq");
        //        System.out.println("------------old return:" +
        //                           SignUtil.checkSignature(wam.getTokeString(), signature, timestamp, nonce));
        if (wam.getOperator().checkSignature(signature, timestamp, nonce)) {
            Logger.getLogger(CoreServlet.class).warn("ƥ��ɹ�");
            out.print(echostr);
        } else {
            Logger.getLogger(CoreServlet.class).warn("û��ƥ��");
            out.print(echostr);
        }
        out.close();
        out = null;
    }

    /**
     * ����΢�ŷ�������������Ϣ
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");        
        //��������
        try {
            
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            System.out.println("yclt begin post:"+requestMap);
           
            PrintWriter out = response.getWriter(); //���Իظ��մ�
            out.println("");
            out.close();
            WxBeanFactory wf=WxBeanFactoryImpl.getInstance();
            String appName=wf.getBean("wxAppDao", WxAppDao.class).findByUserName(requestMap.get("ToUserName")).getAppName();
            WxAppManager wam = wf.getWxAppManager(appName);
            WxUserMsg wum = wam.saveUserMsg(requestMap);
            wam.dispUserMsg(wum); 
            {//�����������Ժ���������
                String openId=requestMap.get("FromUserName");
                wam.getWxUserBo(openId).receiveMail(false);
                wam.getWxUserBo(openId).receiveMailArticle(false);           
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
