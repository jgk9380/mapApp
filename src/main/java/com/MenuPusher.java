package com;

import com.wx.mid.base.menu.*;
import com.wx.mid.operator.WxManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;

@Component
//todo 运行一次，推送菜单
public class MenuPusher {
    @Autowired
    WxManager wxManager;

    Button knowledge() {
        Button buttons[] = new Button[3];
        buttons[0] = new ClickButton("通信新闻", "news");//推送图文
        buttons[1] = new ClickButton("通信趣事", "fun");//推送图文
        buttons[2] = new ClickButton("我的收藏", "favorite");//推送图文
        ComplexButton complexButton = new ComplexButton("通信知识", buttons);
        return complexButton;
    }

    Button financing() {
        Button buttons[] = new Button[3];
        buttons[0] = new ViewButton("腾讯大王卡", "http://www.sohu.com");
        buttons[1] = new ViewButton("无限量卡", "http://www.sohu.com");
        buttons[2] = new ViewButton("联通36卡", "http://www.sohu.com");
        ComplexButton complexButton = new ComplexButton("通信理财", buttons);
        return complexButton;
    }

    Button me() {
        Button buttons[] = new Button[3];
        buttons[0] = new ViewButton("我的信息", "http://www.sohu.com");
        buttons[1] = new ViewButton("号码绑定", "http://www.sohu.com");
        buttons[2] = new ViewButton("有奖活动", "http://www.sohu.com");
        ComplexButton complexButton = new ComplexButton("@我", buttons);
        return complexButton;
    }

    Menu getMenu() {
        Button buttons[] = new Button[3];
        buttons[0] = this.knowledge();
        buttons[1] = this.financing();
        buttons[2] = this.me();
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
