package com.wx.web.servlet;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxUserDao;
import com.wx.mid.entity.WxUser;
import com.wx.mid.util.GGKPicUtils;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(name = "ShowQrCode", urlPatterns = { "/showqrcode" })
public class ShowQrCode extends HttpServlet {
    @SuppressWarnings("compatibility:475108535399448914")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=GBK";
    private static final String JPG = "image/jpeg;charset=GB2312";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("gb2312");
        response.setCharacterEncoding("gb2312");
        String userId = request.getParameter("userId");
        
        if (userId == null)
            userId = "11333";
        //���ͼƬ�����͵ı�־������
        GGKPicUtils ggkp = new GGKPicUtils();

        WxBeanFactoryImpl wbfi = (WxBeanFactoryImpl) WxBeanFactoryImpl.getInstance();
        WxUser wxUser=wbfi.getBean("wxUserDao", WxUserDao.class).findById(Long.parseLong(userId));
       
        byte[] pictA = wbfi.getUserBo(wxUser).getQrCode();
        OutputStream output = response.getOutputStream();
        //�õ������

        //ʹ�ñ��봦���ļ����������
        {
            response.setContentType(JPG);
            //�趨���������
            //�õ�ͼƬ����ʵ·��
            //imagePath = getServletContext().getRealPath(imagePath);
            //�õ�ͼƬ���ļ���
            InputStream imageIn = new ByteArrayInputStream(pictA);
            ;
            //�õ�����ı����������ļ�������jpg��ʽ����
            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
            //�õ�������ͼƬ����
            BufferedImage image = decoder.decodeAsBufferedImage();
            //�õ�����ı�����
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
            encoder.encode(image);
            //��ͼƬ�����������
            imageIn.close();
            //�ر��ļ���
        }

        output.close();
    }
}

