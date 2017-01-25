package com.entity.p;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "LoginUsers.findAll", query = "select o from LoginUsers o") })
@Table(name = "LOGIN_USERS")
public class LoginUsers implements Serializable {
    @SuppressWarnings("compatibility:7284071205528746824")
    private static final long serialVersionUID = 1L;
    @Id
      @Column(nullable = false, length = 20)
      private String name;

      @Column(length = 20)
      private String password;
      @Column(length = 20, name = "EMP_ID")
      private String empId;

      @ManyToOne(optional = true, fetch = FetchType.EAGER)
      @JoinColumn(name = "EMP_ID", insertable = false, updatable = false)
      private Employees employees;

      @Column(name = "isvalid")
      private int isValid;

      @Column(name = "MANAGER_TARGET_ID")
      private String managerTargetId;

      @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
      @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "NAME") },
                 inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
      List<SystemRole> userRoles;


      public LoginUsers() {
      }

      public LoginUsers(Employees employees, String name, String password) {
            this.employees = employees;
            this.name = name;
            this.password = password;
      }


      public String getName() {
            return name;
      }

      public void setName(String name) {
            this.name = name;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public Employees getEmployees() {
            return employees;
      }

      public void setEmployees(Employees employees) {
            this.employees = employees;
      }

      public void setIsValid(int isValid) {
            this.isValid = isValid;
      }

      public int getIsValid() {
            return isValid;
      }

      public void setEmpId(String empId) {
            this.empId = empId;
      }

      public String getEmpId() {
            return empId;
      }

      public void setUserRoles(List<SystemRole> userRoles) {
            this.userRoles = userRoles;
      }


      public List<SystemRole> getUserRoles() {
            return userRoles;
      }

      public int getRoleSize() {
            return userRoles.size();
      }





      public Map<String, Boolean> getUserHasRoleMap() {
            Map<String, Boolean> l = new HashMap<String, Boolean>();
            for (SystemRole sr : this.getUserRoles()) {
                  l.put(sr.getName(), true);
            }
            return l;
      }


      public void setManagerTargetId(String managerTargetID) {
            this.managerTargetId = managerTargetID;
      }

      public String getManagerTargetId() {
            return managerTargetId;
      }
}
