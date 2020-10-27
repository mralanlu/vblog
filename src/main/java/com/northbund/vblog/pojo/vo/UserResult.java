package com.northbund.vblog.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserResult {
    private Long id; // 用户的唯一标识

    private String name;

    private Integer gender;

    private Integer age;

    private Integer score;

    private String phone;

    private String account;

    private String pwd;

    private String address;

    private String email;

    private Integer continueSignIn;

    private Date createTime;

    private Date lastLoginTime;

    private Integer status;

    private String avatar;

    private String roleCode;

    private String roleName;

}
