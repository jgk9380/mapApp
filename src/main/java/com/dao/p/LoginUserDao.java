package com.dao.p;

import com.entity.p.LoginUsers;
import com.entity.s.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jianggk on 2017/1/24.
 */
public interface LoginUserDao extends JpaRepository<LoginUsers, Long> {
    LoginUsers findByName(String name);
}
