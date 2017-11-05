package com;

import com.wx.QrCodeCreater;
import com.wx.mid.handle.WxMsgListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication(exclude = JmxAutoConfiguration.class)
//@EnableConfigurationProperties({SmsServieImpl.class})
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class Server implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("----------AppBootTestStarter main started.");
        ConfigurableApplicationContext ctx = SpringApplication.run(Server.class, args);
    }

    @Autowired
    MenuPusher menuPusher;
    @Autowired
    QrCodeCreater qrCodeCreater;
    @Autowired
    WxMsgListener wxMsgListener;
    @Override
    public void run(String... strings){
        //创建菜单
        //boolean b = menuPusher.pushMenu();
        //todo 方便测试代码，打包时去除
        wxMsgListener.startListen();
        //创建二维码
        //qrCodeCreater.create500PermQrCode();
        //qrCodeCreater.create500QrCode();
    }

}


