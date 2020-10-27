package com.northbund.vblog.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserParam extends PageParam{
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    private Integer status;

    private String avatar;

    private String verifyCode;

    private String msgId;

    private Boolean visitorFlag = false;

}
