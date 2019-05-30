package com.bfd.api.user.domain;

import java.util.Date;

public class User {
    private Long id;

    private String name;

    private String pwd;

    private Integer type;
    
    private String descr;

    private Integer status;

    private Date addTime;

    public User() {
    }
    
    public User(Long id, String name, String pwd, Integer type, String descr, Integer status, Date addTime) {
        super();
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.type = type;
        this.descr = descr;
        this.status = status;
        this.addTime = addTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}