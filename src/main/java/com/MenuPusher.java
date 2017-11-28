package com;

import com.wx.mid.base.menu.*;
import com.wx.mid.operator.WxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;

import java.io.UnsupportedEncodingException;

@Component
//todo 运行一次，推送菜单
//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http%3A%2F%2Fwww.cu0515.com%2F%23%2Fbind&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
//http://www.cu0515.com/?code=011In5ag1ycxXy03rbag1DHK9g1In5aA&state=STATE#/bind
public class MenuPusher {
    @Autowired
    WxManager wxManager;
    String baseUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b" +
            "&redirect_uri=http://www.cu0515.com/URL&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
    Button knowledge() {
        Button buttons[] = new Button[3];
        String url=baseUrl.replace("URL","articlelist.html").replace("STATE","1");
        buttons[0] = new ViewButton("通信新闻", url);
        url=url.replace("STATE","2");
        buttons[1] = new ViewButton("通信趣事", url);//推送图文
        url=url.replace("STATE","3");
        buttons[2] = new ViewButton("我的收藏", url);//推送图文
        ComplexButton complexButton = new ComplexButton("通信知识", buttons);
        return complexButton;
    }

    Button financing() {
        Button buttons[] = new Button[2];
        String url=baseUrl.replace("URL","bill.html");
        buttons[0] = new ViewButton("腾讯大王卡", url);
        //url=baseUrl.replace("URL","http://m.10010.com/mobilegoodsdetail/341703169183.html?setkey=WM_INFO&setvalue=o2m%5E%5F%5E5014090900008196%5E%5F%5E3406309919%5E%5F%5E%25E9%2597%25B5%25E6%2595%258F%5E%5F%5E34a1797%5E%5F%5Ewww%2E10010%2Ecom%5E%5F%5E1030100%5E%5F%5E34%5E%5F%5E34a2e6%5E%5F%5E117%5E%5F%5E3434a1797&from=singlemessage&isappinstalled=1");
        String noLimitUrl="http://m.10010.com/mobilegoodsdetail/341703169183.html?setkey=WM_INFO&setvalue=o2m%5E%5F%5E5014090900008196%5E%5F%5E3406309919%5E%5F%5E%25E9%2597%25B5%25E6%2595%258F%5E%5F%5E34a1797%5E%5F%5Ewww%2E10010%2Ecom%5E%5F%5E1030100%5E%5F%5E34%5E%5F%5E34a2e6%5E%5F%5E117%5E%5F%5E3434a1797&from=singlemessage&isappinstalled=1";
        buttons[1] = new ViewButton("无限量卡", noLimitUrl);
        //buttons[2] = new ViewButton("联通36卡", "http://www.sohu.com");
        ComplexButton complexButton = new ComplexButton("通信理财", buttons);
        return complexButton;
    }

    Button me(){
        Button buttons[] = new Button[1];

        String url= "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http%3A%2F%2Fwww.cu0515.com%2F%23%2Fbind&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
        buttons[0] = new ViewButton("号码绑定", url);
        //buttons[2] = new ViewButton("有奖活动", "http://www.sohu.com");
        ComplexButton complexButton = new ComplexButton("@我", buttons);
        //return complexButton;
        return buttons[0];
    }

    Menu getMenu() {
        Button buttons[] = new Button[2];
       // buttons[0] = this.knowledge();
        buttons[0] = this.financing();
        buttons[1] = this.me();
        Menu menu = new Menu(buttons);
        return menu;
    }


    public boolean pushMenu() {
        boolean b = wxManager.getWxOperator().createMenu(this.getMenu());
        System.out.println("菜单推送：" + b);
        return b;
    }


//    @Override
//    public void run(String... strings) throws Exception {
//        wxManager.getWxOperator().createMenu(this.getMenu());
//    }

}
