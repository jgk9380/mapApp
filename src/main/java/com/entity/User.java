package com.entity;

import javax.persistence.*;

/**
 * Created by jianggk on 2016/10/20.
 */
@Entity
@Table(name = "usertest")
public class User {
    @Id

    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    @Column()
    private Boolean sex;

    public User() {
    }

    public User(String name, int age, long id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
