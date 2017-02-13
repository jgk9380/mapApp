package com.dao.p;

import com.entity.p.Employee;
import com.entity.p.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jianggk on 2017/1/20.
 */
public interface EmployeeDao extends JpaRepository<Employee, String> {
    Employee findByName(String name);
}
