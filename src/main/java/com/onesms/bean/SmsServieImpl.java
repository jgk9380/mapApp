package com.onesms.bean;



import com.onesms.ws.SmsStub;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;

import java.util.Date;

@Component
@Scope("singleton")
@ConfigurationProperties(locations = "classpath:config/smsService.properties", prefix = "sms")
public class SmsServieImpl implements SmsService {
   //  @Value("${sms.corpId}")
    private String corpId;
    // @Value("${sms.loginId}")
    private String loginId;
    //  @Value("${sms.pwd}")
    private String pwd; //�??yclt123
    //  @Value("${sms.url}")
    private String url; //"http://sms.api.ums86.com:8899/sms_hb/services/Sms?wsdl"

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    //    @Override
    //    public String sendSms(String tele, String content) {
    //        // TODO Implement this method
    //        return null;
    //    }

    @Override
    public String sendSms(String tele, String content) {
        try {
            SmsStub stub = new SmsStub(url);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            //String sms="亲爱的蒋先生：您好！为了让您的手机更安全、更快，更大容量，联通公司开展公益换卡啦（号码不变）！本机编辑短信“HK#姓名#收货地址”发送到10010（仅限省内，地址标准格式：江苏省**市***），免费寄送新卡。收到新卡后，先备份好您的老卡通讯录,再使用老卡编辑短信“HK#新卡背面的ICCID后8位”发送到10010，轻松激活新卡，老卡作废。换卡用户可获赠10元话费、三个月合计150分钟本地通话时长、二个月合计4GB省内流量。详情请咨询您的服务经理陈经理：15651554730或10010。";
            //发送接口
            SmsStub.Sms sms0 = new SmsStub.Sms();
            sms0.setIn0(this.corpId);//企业编号
            sms0.setIn1(this.loginId);//登录名
            sms0.setIn2(pwd);//密码
            sms0.setIn3(content);//短信内容
            sms0.setIn4(tele);//手机号码
            sms0.setIn5("000000" + format.format(new Date()));
            sms0.setIn6("");
            sms0.setIn7("1");
            sms0.setIn8("");
            SmsStub.SmsResponse resp = stub.Sms(sms0);
            String res = resp.getOut();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public SmsServieImpl() {
        super();
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpId() {
        return corpId;
    }


    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public static void maintest(String[] args) {
        //String re = WxBeanFactoryImpl.getInstance().getUserService().sendSmsCode("15651554341", "1234");
        //        System.out.println(re);
        //        if (re.contains("result=0")) {
        //            System.out.println("pl");
        //        }
    }
}

