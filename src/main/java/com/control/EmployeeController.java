package com.control;

import com.dao.p.EmployeeDao;
import com.entity.p.Employee;
import com.entity.p.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jianggk on 2017/2/13.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;
    @RequestMapping("/{userId}")//获取员工信息,登录时获取员工登录信息传送给客户端。
    Employee getEmp(@PathVariable String userId) {
        Employee lu= employeeDao.findByName(userId);
        return lu;
    }

    @RequestMapping("/ ")//获取所有员工信息
    List<Employee> getEmps() {
        List<Employee> lu= employeeDao.findAll();
        return lu;
    }
}
