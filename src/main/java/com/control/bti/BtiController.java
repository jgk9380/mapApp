package com.control.bti;

import com.dao.p.BtiAgentDao;
import com.dao.p.BtiOrderDao;
import com.dao.p.LoginUserDao;
import com.entity.p.BtiOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bti")
public class BtiController {
    @Autowired
    BtiOrderDao btiOrderDao;
    @Autowired
    BtiAgentDao btiAgentDao;
    @Autowired
    LoginUserDao loginUserDao;
    @Autowired
    BtiService bs;


    @RequestMapping(value = "/getUnCheckedOverOrder",method = RequestMethod.GET)

    List<BtiOrder> getUncheckedOrder(Principal p ) {//未处理机处理中订单
        return  bs.getUncheckedOrder(p.getName());
    }

    @RequestMapping(value = "/getOrder/{id}",method = RequestMethod.GET)

    BtiOrder getdOrder(Long id ) {//未处理机处理中订单
        return  btiOrderDao.findById(id);
    }
    @RequestMapping(value = "/addOrder",method = RequestMethod.POST)//获取员工信息,登录时获取员工登录信息传送给客户端。
    BtiOrder addOrder(@RequestBody Map map, Principal principal) {
        String certName=(String)map.get("certName");
        String certId=(String)map.get("certId");
        String type=(String)map.get("type");
        String orderRemark=(String)map.get("orderRemark");
        BtiOrder btiOrder =new BtiOrder();
        btiOrder.setType(type);
        btiOrder.setCertName(certName);
        btiOrder.setCertId(certId);
        btiOrder.setOrderRemark(orderRemark);
        btiOrder.setOrderLoginUserId(principal.getName());
        return btiOrderDao.save(btiOrder);
    }

    @RequestMapping(value = "/checkOrder/{id}",method = RequestMethod.PATCH)//
    BtiOrder checkOrder(@PathVariable Long id,Principal principal) throws Exception {
        BtiOrder btiOrder =btiOrderDao.findById(id);
        if(null==btiOrder)
            throw new Exception("无此工单");
        btiOrder.setCheckTime(new Date());
        btiOrder.setStatus(1);
        btiOrder.setCheckerLoginUserId(principal.getName());
        return btiOrderDao.save(btiOrder);
    }

    @RequestMapping(value = "/checkOverOrder/{id}",method = RequestMethod.PATCH)//
    BtiOrder checkOverOrder(@PathVariable Long id,@RequestBody Map map,Principal principal) throws Exception {//只能是自己处理中的订单。
        BtiOrder btiOrder =btiOrderDao.findById(id);
        if(null==btiOrder)
            throw new Exception("无此工单");
        if(!btiOrder.getCheckerLoginUserId().equals(principal.getName())) {
            throw new Exception("工单不是当前处理人");
        }
        btiOrder.setCheckOverTime(new Date());
        btiOrder.setStatus(2);
        String checkOverRemart= (String) map.get("checkOverRemart");
        btiOrder.setCheckOverRemark(checkOverRemart);
        return btiOrderDao.save(btiOrder);
    }
    //todo 有订单提醒客户端
}


//POST（CREATE）：在服务器新建一个资源。
//PUT（UPDATE） ： 在服务器更新资源（客户端提供改变后的完整资源）。
//PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。