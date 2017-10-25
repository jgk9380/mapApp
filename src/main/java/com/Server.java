package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(exclude = JmxAutoConfiguration.class)
//@EnableConfigurationProperties({SmsServieImpl.class})
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class Server implements CommandLineRunner {


    public static void main(String[] args) {
        System.out.println("----------AppBootTestStarter main started.");
        ConfigurableApplicationContext ctx = SpringApplication.run(Server.class, args);
    }



    @Autowired
    MenuPusher menuPusher;
    @Override
    public void run(String... strings) {
       // boolean b = menuPusher.pushMenu();

    }

}


