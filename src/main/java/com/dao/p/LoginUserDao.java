package com.dao.p;

import com.entity.p.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by jianggk on 2017/1/24.
 */
@Transactional
public interface LoginUserDao extends JpaRepository<LoginUser, Long> {
    LoginUser findByName(String name);
}
