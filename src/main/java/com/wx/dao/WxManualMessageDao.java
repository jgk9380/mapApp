package com.wx.dao;

import com.wx.entity.WxManualMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface WxManualMessageDao extends CrudRepository<WxManualMessage,Integer> {
    WxManualMessage findById(int id);
    List<WxManualMessage> findByWxUserIdOrderByReceivedDate(int wxUserId);
    @Query("select o.wxUserId,count(o.content) from WxManualMessage o where o.replyFlag=0")
    List<Map<String,Integer>> findToReplayUsers();
}
