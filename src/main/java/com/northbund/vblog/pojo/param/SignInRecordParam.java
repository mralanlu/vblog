package com.northbund.vblog.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SignInRecordParam {
    /**
     * id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Long userId;

    /**
     * 签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signInDate;

    /**
     * 连续签到标识
     */
    private Boolean continueSignInFlag;

    /**
     * 查询开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startSignInDate;

    /**
     * 查询签到日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endSignInDate;


}
