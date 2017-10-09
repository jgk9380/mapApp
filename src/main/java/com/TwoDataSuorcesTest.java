package com;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jianggk on 2017/1/20.
 */
//@Component
//@Order(value = 3)
//@SpringBootApplication
public class TwoDataSuorcesTest  implements CommandLineRunner {
//    public static void main1(String[] args) {
//        ConfigurableApplicationContext ctx = SpringApplication.run(TwoDataSuorcesTest.class, args);
//    }


    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate1;
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate2;

    @Override
    public void run(String... strings) throws Exception {
        setUp();
        test();
    }


    public void setUp() {
        jdbcTemplate1.update("DELETE  FROM  ttt ");
       // jdbcTemplate2.update("DELETE  FROM  ttt ");
    }

    public void test() throws Exception {
        // 往第一个数据源中插入两条数据
        jdbcTemplate1.update("insert into ttt(id,name,age) values(?, ?, ?)", 1, "aaa", 20);
        jdbcTemplate1.update("insert into ttt(id,name,age) values(?, ?, ?)", 2, "bbb", 30);
        // 往第二个数据源中插入一条数据，若插入的是第一个数据源，则会主键冲突报错
        jdbcTemplate2.update("insert into ttt(id,name,age) values(?, ?, ?)", 1, "aaa", 20);
        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
        //Assert.assertEquals("2", jdbcTemplate1.queryForObject("select count(1) from ttt", String.class));
        // 查一下第一个数据源中是否有两条数据，验证插入是否成功
       //Assert.assertEquals("1", jdbcTemplate2.queryForObject("select count(1) from ttt", String.class));
    }
}
