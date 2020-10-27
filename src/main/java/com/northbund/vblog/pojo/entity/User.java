package com.northbund.vblog.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity // 实体
@Data
@Table(name="user")
public class User implements  Serializable {
    private static final long serialVersionUID = 1L;
    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id; // 用户的唯一标识

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "score")
    private Integer score;

    @Column(name = "phone")
    private String phone;

    @Column(name = "account")
    private String account;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "continue_sign_in")
    private Integer continueSignIn;

    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "avatar")
    private String avatar;

}
