package com.wx.mid.operator;



import com.wx.mid.base.menu.*;
import com.wx.mid.base.pojo.Token;
import com.wx.mid.base.util.CommonUtil;
import com.wx.mid.base.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class MenuManagerOld {
    String viewMenuAuthUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URI&response_type=RTYPE&scope=SCOPE&state=STATE#wechat_redirect";
    String appName;
    public MenuManagerOld(String appName){
        this.appName=appName;
    }
    private static Logger log = LoggerFactory.getLogger(MenuManagerOld.class);

    /**
     * ����˵��ṹ     
     * @return
     */
    private Menu getMenuTest() {
        ClickButton btn11 = new ClickButton("��ά�ƹ�", "ertg");
        ViewButton btn21 =
            new ViewButton("�ιο�",
                           "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b" +
                           "&redirect_uri=http://www.ycunicom.com/wx2/oAuthServlet?target=ggk&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        ClickButton btn22 = new ClickButton("����һЦ", "kxyx");
        ViewButton btn23 =
            new ViewButton("�ҵ��˱�",
                           "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http://www.ycunicom.com/wx2/oAuthServlet?target=account&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        ViewButton btn24 =
            new ViewButton("��Ǯ��",
                           "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http://www.ycunicom.com/wx2/oAuthServlet?target=money&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        ViewButton btn25 =
            new ViewButton("��è",
                           "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http://www.ycunicom.com/wx2/oAuthServlet?target=money&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        ComplexButton mainBtn2 = new ComplexButton("�����н�", new Button[] { btn21, btn22, btn23,btn24,btn25 });
        ViewButton btn31 =
            new ViewButton("�󶨺���",
                           "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7dcc6b2e03a47c0b&redirect_uri=http://www.ycunicom.com/wx2/oAuthServlet?target=bind&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
        
        ClickButton btn33 = new ClickButton("ҵ���ѯ", "ywcx");
        ComplexButton mainBtn3 = new ComplexButton("��������", new Button[] { btn31,  btn33 });
        
        Menu menu = new Menu(new Button[] { btn11, mainBtn2,mainBtn3 });


        return menu;
    }
    
    private  static  Menu getMenu() {
        return null;
    }
    
    private void PushMenu(){
        
    }
    public static void main(String[] args) {
        
        // �������û�Ψһƾ֤
        String appId = "wx7dcc6b2e03a47c0b";
        // �������û�Ψһƾ֤��Կ
        String appSecret = "fb845f65afd06d318e7a961d867f877f";

        // ���ýӿڻ�ȡƾ֤
        Token token = CommonUtil.getToken(appId, appSecret);

        if (null != token) {
            // �����˵�
            boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());
            // �жϲ˵��������
            if (result)
                log.info("�˵������ɹ���");
            else
                log.info("�˵�����ʧ�ܣ�");
        }
    }
}

