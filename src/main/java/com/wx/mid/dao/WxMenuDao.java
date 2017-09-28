package com.wx.mid.dao;

import java.util.List;


import com.wx.mid.entity.menu.WxClickMenu;
import com.wx.mid.entity.menu.WxMenu;
import com.wx.mid.entity.menu.WxViewMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WxMenuDao extends CrudRepository<WxMenu, String> {
    @Query("select u from WxMenu u where u.parentMenu = ?1 and u.valid=true  order by u.id")
    List<WxMenu> findByParentMenu(WxMenu pm);

    WxMenu findById(String id);

    @Query("select u from WxMenu u where u.wxApp.id = ?1 and u.parentMenu is null order by u.id")
    List<WxMenu> findAppRootMenus(String appId);
    
    @Query("select u from WxViewMenu u where u.wxApp.appName = :appName and u.targetUrl=:target ")
    WxViewMenu findByTargetUrl(@Param("appName") String appName, @Param("target") String target);
    @Query("select u from WxClickMenu u where u.wxApp.appName = :appName and u.key=:key ")
    WxClickMenu findByKey(@Param("appName") String appName, @Param("key") String key);
    
}
