package com.wx.mid.operator.msg;

import java.util.Date;


import com.wx.mid.entity.WxUserMsg;
import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.dao.WxAppDao;
import com.wx.mid.dao.WxUserMsgDao;
import com.wx.mid.operator.WxAppManager;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;




@Component("referMsgActionListener")

public class MsgActionListenerRefereeImpl implements MsgActionListener {
    public MsgActionListenerRefereeImpl() {
        super();
    }
    @Autowired
    WxUserMsgDao wxUserMsgDao;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    WxAppDao wxAppDao;

    @Override
    public String doAction(WxUserMsg wum) {
        String result = null;
        String jsonString = wum.getSceneArgs();
        JSONObject json = JSONObject.fromObject(jsonString);
        result = "���Ƽ����û�'" + json.getString("nickName") + "'��ע���γ���ͨ��Ŀǰ���ɹ��Ƽ���(�����˶�)";
        //TODO �޸� bo.getRefereeCount
        String sql =
            "select count(*) co from wx_user " +
            "where   SUBSCRIBE_status=1  and referee_id=" + wum.getWxUser().getId();
        System.out.println("sql=" + sql);
        int refereeCount = jdbcTemplate.queryForObject(sql, Integer.class);
        result = result + refereeCount + "�û���ע,����Ŭ��,�󽱵����㣡";
        wum.setHandleTime(new Date());
        wum.setHandleResult("�ظ��û���" + result);
        WxAppManager wam =
            WxBeanFactoryImpl.getInstance().getWxAppManager(wum.getWxUser().getWxApp().getAppName());
        wam.getOperator().sendTxtMessage(wum.getWxUser().getOpenId(), result);
        wxUserMsgDao.save(wum);
        return null;
       
    }
}
