package com.control;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.faces.model.SelectItem;
import java.util.List;

/**
 * Created by jianggk on 2017/2/13.
 */

@RestController
@RequestMapping("/option")
public class OptionController {
    @RequestMapping("/depart")
    List<SelectItem> getDepartSelectItems(){
        return null;
    }
    @RequestMapping("/city")
    List<SelectItem> getcitySelectItems(){
        return null;
    }

    @RequestMapping("/grid")
    List<SelectItem> getGridSelectItems(){
        return null;
    }

    @RequestMapping("/channel")
    List<SelectItem> getChannelSelectItems(){
        return null;
    }

    @RequestMapping("/position")//岗位
    List<SelectItem> getPositionSelectItems(){
        return null;
    }

}
