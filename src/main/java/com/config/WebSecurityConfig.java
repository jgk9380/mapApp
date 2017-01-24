package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

/**
 * Created by jianggk on 2016/11/7.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("primaryDataSource")
    DataSource ds;
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
//        http.httpBasic().and().authorizeRequests()
//                .antMatchers(        "/api/**","/user")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //对于Angular2来说，需要设置CSRF token存储，否则浏览器没有办法取得正确的CSRF token，


        //http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        System.out.println("ds="+ds);
//        auth.inMemoryAuthentication()
//                .withUser("jgk").password("jianggk").roles("admin");
//        auth.inMemoryAuthentication()
//                .withUser("jianggk").password("jianggk").roles("admin");
        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery("select name,password,isvalid from login_users where name=?")
                .authoritiesByUsernameQuery("select username,auth from (\n" +
                        "select u.name username,a.name auth\n" +
                        "from login_users u ,jemtest.j_role r ,jemtest.J_AUTHORITY  a,jemtest.j_user_role ur,jemtest.J_ROLE_AUTH ra\n" +
                        "where ur.uname=u.name and ur.rname=r.name\n" +
                        "and  ra.rname=r.name and ra.aname=a.name\n" +
                        "union \n" +
                        "select  u.name username,a.name auth  from login_users u,jemtest.J_AUTHORITY a, jemtest.J_USER_AUTH ua\n" +
                        "where u.name=ua.uname and ua.aname=a.name\n" +
                        ") where username=?");
    }

}
