package com;

import com.bean.Person;
import com.onesms.bean.SmsService;
import com.onesms.bean.SmsServieImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class Starter {
    public static void main(String[] args) {
        System.out.println("----------AppBootTestStarter main started.");
        try {
            //DingRobotTest.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //以下二行一个效果
        ConfigurableApplicationContext ctx= SpringApplication.run(CCSSmsSenderImpl.class, args);
        // AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Starter.class);
        //System.out.println("&&&&&age="+ctx.getBean(Person.class).getAge());
    }
}
