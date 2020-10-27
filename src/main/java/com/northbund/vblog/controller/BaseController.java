package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alan Lu
 */
public class BaseController {

    private Logger log = LoggerFactory.getLogger(BaseController.class);


    protected JSONObject representation(ResultCodeEnum msg) {
        JSONObject json = new JSONObject();
        if(msg==null||msg.getCode()==null){
            json.put("resultCode",10000);
        }else{
            json.put("resultCode", msg.getCode().toString());
        }
        json.put("msg", msg.getDesc());
        return json;
    }



    protected JSONObject representation(String msg) {
        JSONObject json = new JSONObject();
        json.put("resultCode", 1234);
        json.put("msg",msg);
        return json;
    }

    protected JSONObject representation(ResultCodeEnum msg, String message) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        if(null!=message&&!message.equals("")){
            json.put("msg", msg.getDesc()+":"+message);
        }else{
            json.put("msg", msg.getDesc());
        }
        return json;
    }

    protected JSONObject representationCustomized(ResultCodeEnum msg, String message) {
        return representationCustomized(msg, message, null);
    }

    protected JSONObject representationCustomized(ResultCodeEnum msg, String message, Object extraData) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", StringUtils.isBlank(message) ? msg.getDesc() : msg.getDesc() + ":" + message);
        if (extraData != null) {
            json.put("extraData", extraData);
        }
        return json;
    }

    protected JSONObject representationCompletelyCustomized(ResultCodeEnum msg, String message) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", message);
        return json;
    }

    protected JSONObject representation(ResultCodeEnum msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.getDesc());
        json.put("data", data);
        return json;
    }

    protected JSONObject wrapException(Exception e) {
        JSONObject json = new JSONObject();
        try{
            String errEnum = e.getMessage().split(":")[0];
            ResultCodeEnum codeEnum = ResultCodeEnum.getCodeByDesc(errEnum);
            if (codeEnum != null) {
                Integer resultCode = codeEnum.getCode();
                String msg = e.getMessage();
                json.put("resultCode", resultCode.toString());
                json.put("msg", msg);
                log.error("[" + resultCode + "]" + msg, e.getMessage());
            } else {
                json.put("resultCode", ResultCodeEnum.SYSTEM_ERROR.getCode());
                json.put("msg", ResultCodeEnum.SYSTEM_ERROR.getDesc());
                log.error("[system error]:" + e.getMessage());

            }
        }catch (Exception e2){
            json.put("resultCode", ResultCodeEnum.SYSTEM_ERROR.getCode());
            json.put("msg", ResultCodeEnum.SYSTEM_ERROR.getDesc());
            log.error("[system error]:");
        }
        return json;
    }

}
