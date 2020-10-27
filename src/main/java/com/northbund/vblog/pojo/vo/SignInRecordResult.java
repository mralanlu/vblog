package com.northbund.vblog.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SignInRecordResult {
    /**
     * id
     */
    protected Integer id;

    /**
     * 用户id
     */
    protected Integer userId;

    /**
     * 签到日期
     */
    private Date signInDate;
}
