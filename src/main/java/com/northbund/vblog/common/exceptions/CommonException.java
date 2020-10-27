package com.northbund.vblog.common.exceptions;


import com.northbund.vblog.common.enums.ResultCodeEnum;

/**
 * @author Alan Lu
 **/

public class CommonException extends RuntimeException {

    private ResultCodeEnum message;

    private String msg;

    private Object extraData;

    public CommonException(ResultCodeEnum message) {
        super("");
        this.message = message;
    }

    public CommonException(String message) {
        super(message);
        this.msg = message;
    }

    public CommonException(ResultCodeEnum message, String detail) {
        super(detail);
        this.message = message;
    }

    public CommonException(ResultCodeEnum message, Object extraData) {
        this.message = message;
        this.extraData = extraData;
    }


    public ResultCodeEnum getMessageInfo() {
        return message;
    }

    public String getStrMessage() {
        return msg;
    }

    public Object getExtraData() {
        return extraData;
    }
}
