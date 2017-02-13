package com.dao.p;

import com.entity.p.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jianggk on 2017/1/24.
 */
public interface LoginUserDao extends JpaRepository<LoginUser, Long> {
    LoginUser findByName(String name);

}
