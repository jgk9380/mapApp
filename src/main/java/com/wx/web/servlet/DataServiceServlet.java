package com.wx.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.wx.mid.WxBeanFactoryImpl;
import com.wx.mid.operator.data.service.DataServiceListener;
import net.sf.json.JSONObject;
import org.jboss.logging.Logger;


@WebServlet(name = "commAjaxServlet", urlPatterns = { "/commAjaxServlet" })
public class DataServiceServlet extends HttpServlet {
    @SuppressWarnings("compatibility:3812105103277568233")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=GBK";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("action="+action);
        if (action != null) {
            DataServiceListener sg = WxBeanFactoryImpl.getInstance().getDataServiceListener(action);
            if (sg == null) {
                Logger.getLogger(DataServiceServlet.class).error("û�ж�Ӧ��DataService��" + action);
                return;
            }
            JSONObject result = null;
            try {
                result = sg.doGet(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            if(result!=null)
            out.println(result.toString());
            else
                out.println("");
            out.close();
            return;
        }
        Logger.getLogger(DataServiceServlet.class).error("û��aciton����");
    }
}
