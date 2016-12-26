package com.entity;

/**
 * Created by jianggk on 2016/10/25.
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;

/**
 * Created by jianggk on 2016/10/20.
 */
@Entity
@Table(name = "change_card")
public class ChangeCard {
    @Id
    @Column(length = 11)
    String deviceNumber;
    String familyName;
    String sex;
    String managerFamilyName;
    String managerTele;

    public String getDevcieNumber() {
        return deviceNumber;
    }

    public void setDevcieNumber(String devcieNumber) {
        this.deviceNumber = devcieNumber;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getManagerFamilyName() {
        return managerFamilyName;
    }

    public void setManagerFamilyName(String managerFamilyName) {
        this.managerFamilyName = managerFamilyName;
    }

    public String getManagerTele() {
        return managerTele;
    }

    public void setManagerTele(String managerTele) {
        this.managerTele = managerTele;
    }
}
