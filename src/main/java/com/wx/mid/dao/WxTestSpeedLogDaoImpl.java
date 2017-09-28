package com.wx.mid.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;


import com.wx.mid.entity.WxTestSpeedLog;
import com.wx.mid.WxBeanFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class WxTestSpeedLogDaoImpl {   
    @Autowired
    @PersistenceContext
    private EntityManager em;
    
    public boolean refershData(){
        em.getEntityManagerFactory().getCache().evictAll();
        return true;
    }

    public WxTestSpeedLog findMaxSpeedByUserId(Long userId) {
       
        String jpql =
            "select u from WxTestSpeedLog  u where u.userId = :userId " +
            "and u.speed=(select max(a.speed) from WxTestSpeedLog a where a.userId=:userId)";
        TypedQuery<WxTestSpeedLog> tq = em.createQuery(jpql, WxTestSpeedLog.class).setParameter("userId", userId);
        List<WxTestSpeedLog> relist = tq.getResultList();
        if (relist.size() > 0)
            return relist.get(0);
     
        return null;
    }

    public static void main(String[] args) {
        WxTestSpeedLogDao wtsld = WxBeanFactoryImpl.getInstance().getBean("wxTestSpeedLogDao", WxTestSpeedLogDao.class);
        System.out.println(wtsld);
        WxTestSpeedLog wtsl = wtsld.findMaxSpeedByUserId(3007095l);
        System.out.println(wtsl.getSpeed());
    }
}
