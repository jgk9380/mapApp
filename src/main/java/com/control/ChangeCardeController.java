package com.control;

import com.dao.s.ChargeCardDao;
import com.entity.s.ChangeCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jianggk on 2016/12/1.
 */
@RestController
//@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/ccs")
public class ChangeCardeController {
    @Autowired
    ChargeCardDao chargeCardDao;
    @RequestMapping(path="/{id}",method = RequestMethod.GET)
    //,@CookieValue("JSESSIONID") String sessionId
    public ChangeCard getCc(@PathVariable("id")  String id){
        System.out.println("ChangeCard getCc sessonId=");
        ChangeCard cc = chargeCardDao.findByDeviceNumber(id);
        return cc;
    }
    //@CrossOrigin(origins = "http://localhost:9000")
    //@CrossOrigin(origins = "http://127.0.0.1:8080",maxAge=3600)
    //@CrossOrigin
    @RequestMapping( path="/",method=RequestMethod.GET)
    public List<ChangeCard> getCcList(){
        System.out.println("ChangeCard getCc sessonId=");
        List< ChangeCard> cc = chargeCardDao.findAll();
        return cc;
    }

}
