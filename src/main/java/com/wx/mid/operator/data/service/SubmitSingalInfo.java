package com.wx.mid.operator.data.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.mid.entity.WxSingal;
import com.wx.mid.WxSession;
import com.wx.mid.dao.WxSingalDao;
import com.wx.mid.util.WxUtils;
import net.sf.json.JSONObject;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//submitSingalInfo
@Component("submitSingalInfo")
public class SubmitSingalInfo implements DataServiceListener {
    @Autowired
    WxSingalDao wxSingalDao;

    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // ��֤�ֻ���
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @Override
    public JSONObject doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject json = new JSONObject();
      
        try {
           // System.out.println("submitSingalInfo");
            String param = request.getParameter("param");
           // System.out.println("param=" + param);
            JSONObject paramJson = JSONObject.fromObject(param);
            String tele = paramJson.getString("tele");
            if (!this.isMobile(tele)) {
                json.put("resultInfo", "������ĺ��룡");
                return json;
            }
            String name = paramJson.getString("name");
            String area = paramJson.getString("area");
            String adress = paramJson.getString("adress");
            if (adress == null || adress.length() < 6) {
                json.put("resultInfo", "����д��ϸ��ַ��");
                return json;
            }
            String signalStrength = paramJson.getString("signalStrength");
            String signalDesc = paramJson.getString("signalDesc");
            String other = paramJson.getString("other");
            
            Number latitude = paramJson.getDouble("latitude");
            Number longitude = paramJson.getDouble("longitude");
            WxSingal wxSingal = new WxSingal();
            wxSingal.setId(WxUtils.getSeqencesValue());
            wxSingal.setTele(tele);
            wxSingal.setName(name);
            wxSingal.setArea(area);
            wxSingal.setAddress(adress);
            wxSingal.setSignalStrength(signalStrength);
            wxSingal.setSignalDesc(signalDesc);
            wxSingal.setOther(other);
            wxSingal.setLatitude(latitude);
            wxSingal.setLongitude(longitude);
            WxSession wxSession = (WxSession) request.getSession(true).getAttribute("wxSession");
            if(wxSession!=null){
                wxSingal.setUserId(wxSession.getWxUserBo().getWxUser().getId());
            }
            wxSingalDao.save(wxSingal);
            json.put("resultInfo", "���ϱ��������������յ������ǵĹ�����Ա�ἰʱ������ϵ��");
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            json.put("resultInfo", "����������ݣ�");
            return json;
        }
    }
}
