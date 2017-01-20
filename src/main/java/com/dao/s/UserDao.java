package com.dao.s;

import com.entity.s.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by jianggk on 2016/10/20.
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByNameAndAge(String name, Integer age);

    @Query("select u from User u where u.name=:name")
    User findUser(@Param("name") String name);
}
