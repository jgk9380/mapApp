package com;

import com.bean.Person;
import com.dao.ChargeCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by jianggk on 2016/10/21.
 */

//@Configuration
@Component
@Order(value=2)
//@EnableAutoConfiguration
//@ComponentScan(basePackages = "com , com.onesms.bean")
public class PersonAutoShowImpl implements CommandLineRunner {
    @Autowired
    ApplicationContext ctx;

    @Bean
    public Person getPerson(Integer age) {
        Person tb = new Person();
        tb.setAge(age);
        return tb;
    }

   @Bean
    public Integer getAge() {
        return 18;
    }

//    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppAnnaoTestStarter.class);
//        //oracle.jdbc.driver.OracleDriver oracleDriver;
//    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("age=" + ctx.getBean(Person.class).getAge());
    }
}
