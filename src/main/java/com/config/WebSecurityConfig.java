package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * Created by jianggk on 2016/11/7.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//                    http.authorizeRequests().antMatchers("*").permitAll()
//                            .antMatchers("/", "/home").permitAll()
//                            .anyRequest().authenticated()
//                            .and()
//                                    .rememberMe()
//                                    .tokenValiditySeconds(3600)
//                                    .key("userName")
//                            .and()
//                            .formLogin()
//                            .loginPage("/login")
//                            .permitAll()
//                            .and()
//                            .logout()
//                            .permitAll();
       http.authorizeRequests() .anyRequest().permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("jgk").password("jianggk").roles("admin");
        auth.inMemoryAuthentication()
                .withUser("jianggk").password("jianggk").roles("admin");
    }

}
