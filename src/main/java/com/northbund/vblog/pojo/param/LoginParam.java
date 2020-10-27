package com.northbund.vblog.pojo.param;

import com.northbund.vblog.common.annotation.NullField;
import lombok.Data;

@Data
public class LoginParam {
    @NullField
    private String account;

    @NullField
    private String password;

    private String phone;

    private Long timeMillis;

    private String verifyCode;

    private String verificationCode;


}
