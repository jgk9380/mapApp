package com.wx.mid.dao;


import com.wx.mid.entity.WxTestSpeedLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WxTestSpeedLogDao extends CrudRepository<WxTestSpeedLog, Long> {
    public boolean refershData();
    WxTestSpeedLog findMaxSpeedByUserId(Long id);
    @Query(value =
           "select count(*) from wx_test_speed_log " +
           "where user_id=?1 and to_char(test_date,'YYYYMMDD')=to_char(sysdate,'YYYYMMDD')", nativeQuery = true)
    int findTimesByUserId(Long userId);
 
}
