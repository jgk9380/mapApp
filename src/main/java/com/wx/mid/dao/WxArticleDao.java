package com.wx.mid.dao;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;


import com.wx.mid.entity.WxArticle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WxArticleDao extends CrudRepository<WxArticle, BigDecimal> {
    WxArticle findById(BigDecimal id);

    @Query("select a from WxArticle  a   where " +
           " not exists(select s from  WxArticleSendHistory s where s.artId=a.id and s.userId=:userId) " +
           " and a.limitDate>=:today   and a.sendDate<=:today  order by a.sendDate desc ,a.seq ")
    List<WxArticle> getNews(Pageable pagable, @Param("userId") long userId, @Param("today") Date today);

    @Query("select a from WxArticle  a  where " +
        " a.limitDate>=:today and a.sendDate<=:today order by a.sendDate desc ,a.seq ")
    List<WxArticle> getNearlyNews(Pageable pagable, @Param("today") Date today);
}
