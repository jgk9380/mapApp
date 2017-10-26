package com.wx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/data")
public class WxDataController {
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    JdbcTemplate jdbcTemplate;

    @RequestMapping(path = "/queryBySql",method = RequestMethod.POST)
    List<Map<String,Object>> getDataBySql(@RequestBody(required = true) Map<String, Object> map){
        String sql= (String) map.get("sql");
        List<Map<String,Object>> l=jdbcTemplate.queryForList(sql);
        return l;
    }
}
