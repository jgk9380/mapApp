package com.wx.mid.dao;

import java.math.BigDecimal;

import java.util.List;


import com.wx.mid.entity.WxArticleDiscuss;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WxArticleDiscussDao extends CrudRepository<WxArticleDiscuss, BigDecimal> {
    WxArticleDiscuss findById(BigDecimal id);
    @Query(value="select u from WxArticleDiscuss u where u.artId=?1" )//order by u.discussDate desc
    List<WxArticleDiscuss> findByArtId(Pageable pageRequest, BigDecimal artId);
    @Query(value="select count(u) from WxArticleDiscuss u where u.artId=?1 " )
    int findCountByArtId(BigDecimal artId);
}
