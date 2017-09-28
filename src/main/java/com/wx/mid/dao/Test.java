package com.wx.mid.dao;

import java.lang.reflect.InvocationTargetException;

import java.math.BigDecimal;


import com.wx.mid.WxBeanFactoryImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**   You are using the buggy parser. Please define in the Eclipse Window->Preferences->Tomcat->JVM settings:
        -Djavax.xml.parsers.SAXParserFactory=com.sun.mid.apache.xerces.internal.jaxp.SAXParserFactoryImpl
        -Djavax.xml.parsers.DocumentBuilderFactory=com.sun.mid.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
        Or it must be defined in the VM-Parameters of your Run-Configuration if you use the main()-method.
 */

public class Test {

    public void testFind(String id) {
        //        WxPromotionGift wml = wxPromotionGiftDao.findById(id);
        // System.out.println(wml.getGiftModel().getClass());
    }

    public static void main1(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        WxArticleDiscussDao wxMenuDao = WxBeanFactoryImpl.getInstance().getBean("wxArticleDiscussDao", WxArticleDiscussDao.class);
        int i = wxMenuDao.findCountByArtId(new BigDecimal(2));
        System.out.println(i);
        Pageable pageAble = new PageRequest(10, 5, new Sort(Sort.Direction.DESC, "discussDate"));
//       List<WxArticleDiscuss> l=wxMenuDao.findByArtId(pageAble, new BigDecimal(2));
//        for(WxArticleDiscuss w:l){
//            System.out.println(w.getId());
//        }

    }
}
