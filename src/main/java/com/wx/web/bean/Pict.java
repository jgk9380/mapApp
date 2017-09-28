package com.wx.web.bean;

import com.wx.mid.util.PictUtils;

import java.awt.Image;

import java.awt.image.BufferedImage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;



@ManagedBean(name = "pict")
@SessionScoped
public class Pict {
    public Pict() {
    }

    public Image getImage() {
        PictUtils pictUtil = new PictUtils();
        BufferedImage source = pictUtil.loadImageLocal("D:\\image\\sharebase.jpg");
        return source;
    }

    public static void main(String[] args) {
//        String ticket =
//            WxBeanFactoryImpl.getInstance().getConfigWxAppManager().getOperator().getJsApiTicket().getTicket();
    }
}
