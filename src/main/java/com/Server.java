package com;

import com.entity.p.BtiAgentInitor;
import com.onesms.bean.SmsService;

import com.dao.s.ChargeCardDao;
import com.entity.s.ChangeCard;
import com.onesms.bean.SmsServieImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;


@SpringBootApplication(exclude = JmxAutoConfiguration.class)
//@EnableConfigurationProperties({SmsServieImpl.class})
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class Server implements CommandLineRunner {
    @Autowired
    BtiAgentInitor  btiAgentInitor;
    public static void main(String[] args) {
        System.out.println("----------AppBootTestStarter main started.");

        ConfigurableApplicationContext ctx = SpringApplication.run(Server.class, args);


    }

    //Dao注入
    @Autowired
    ChargeCardDao chargeCarDao;
    //一信通服务注入
    @Autowired
    private SmsService smsService;
    //    @Autowired
    //    @Qualifier("thirdJdbcTemplate")
    //    JdbcTemplate jt;
        //
    //    @Autowired
    //    @Qualifier("thirdDataSource")
    //    DataSource ds;
    //    public static void main(String[] args) throws Exception {
    //        System.out.println("----------ChangeCardSmsSenderStarter main started");
    //       final AplicationContext ctx = SpringApplication.run(CCSSmsSenderImpl.class);
    //    }

    @Override
    public void run(String... strings) {
        //testJdbcTemplate();
        //testDao();
        btiAgentInitor.init();
    }

//    public void testJdbcTemplate() {
//        System.out.println("ds3="+ds);
//        List<Map<String, Object>> res = jt.queryForList("select * from tab");
//        System.out.println("res.size+" + res.size());
//        for (Map<String, Object> m : res) {
//            System.out.println();
//            for (String s : m.keySet()) {
//                System.out.print(s + ".value=" + m.get(s) + "   ");
//            }
//            System.out.println();
//        }
//    }

    public void testDao() {
        System.out.println("----------ChangeCardSmsSenderStarter task started");
        ChangeCard cc = chargeCarDao.findByDeviceNumber("15651554341");
        if (cc != null) {
            System.out.println("corpId=" + ((SmsServieImpl) smsService).getCorpId() + "   cc.id=" + cc.getDevcieNumber());
        } else {
            System.out.println("corpId=" + ((SmsServieImpl) smsService).getCorpId() + "   cc is null ");
        }
        //List<ChangeCard> l = chargeCarDao.findAll();
        //sendSms(smsService,cc);
//        if (cc != null)
//            try {
//                sendSms(smsService, cc);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }

    public boolean sendSms(SmsService os, ChangeCard cc) throws Exception {
        String smsContent = getSMSContent(cc);
        System.out.println("----发送短信：" + cc.getDevcieNumber() + "   内容：" + smsContent);
        String s = os.sendSms(cc.getDevcieNumber(), smsContent);
        System.out.println("s=" + s);
        if (!s.contains("result=0")) throw new Exception("发送短信 失败：" + s);
        //org.apache.axis2.transport.local.LocalTransportSender
        return (s.contains("result=0"));
    }

    public boolean sendSms(SmsService os, List<ChangeCard> ccl) throws Exception {
        for (ChangeCard cc : ccl) {
            boolean b = sendSms(os, cc);
            if (!b) return b;
        }
        return true;
    }

    public String getSMSContent(ChangeCard cc) {
        String sctemp = "亲爱的$fam_name$xn：您好！为了让您的手机更安全、更快，更大容量，联通公司开展公益换卡啦（号码不变）！" +
                "\n1、本机编辑短信“HK#姓名#收货地址”发送到10010（仅限省内，地址标准格式：江苏省**市***），免费寄送新卡。" +
                "\n2、收到新卡后，先备份好您的老卡通讯录,再使用老卡编辑短信“HK#新卡背面的ICCID后8位”发送到10010，轻松激活新卡，老卡作废。" +
                "\n3、换卡用户可获赠10元话费、三个月合计150分钟本地通话时长、二个月合计4GB省内流量。" +
                "\n详情请咨询您的服务经理$mm：$mt或10010。" +
                "\n如卡已更换，请忽略此信息。";
        String smsContent = null;
        if (cc.getSex().equalsIgnoreCase("M"))
            smsContent = sctemp.replace("$fam_name", cc.getFamilyName()).replace("$xn", "先生").replace("$mm", cc.getManagerFamilyName() + "经理").replace("$mt", cc.getManagerTele());

        if (cc.getSex().equalsIgnoreCase("F"))
            smsContent = sctemp.replace("$fam_name", cc.getFamilyName()).replace("$xn", "女士").replace("$mm", cc.getManagerFamilyName() + "经理").replace("$mt", cc.getManagerTele());
        return smsContent;
    }

}


