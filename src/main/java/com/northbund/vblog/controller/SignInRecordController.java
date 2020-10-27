package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.SignInRecordParam;
import com.northbund.vblog.service.SignInRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/signIn")
public class SignInRecordController extends BaseController{

    @Autowired
    private SignInRecordService signInRecordService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject listSignInRecord(@RequestBody SignInRecordParam signInRecordParam) {
        try {
            PageBean pageBean=signInRecordService.findAllByParam(signInRecordParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 签到
     */
    @RequestMapping(value="/signIn", method = RequestMethod.POST)
    public JSONObject addSignInRecord(@RequestBody SignInRecordParam signInRecordParam) {
        try {
            signInRecordService.signIn(signInRecordParam);
            return representation(ResultCodeEnum.SUCCESS, signInRecordParam);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.ERROR_SIGN_IN,e.getMessage());
        }
    }

    /**
     * @description: 修改
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JSONObject updateSignInRecord(@RequestBody  SignInRecordParam signInRecordParam) {
        try {
            signInRecordService.update(signInRecordParam);
            return representation(ResultCodeEnum.SUCCESS, signInRecordParam);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 删除
     */
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public JSONObject deleteSignInRecord(@RequestBody SignInRecordParam signInRecordParam) {
        try {
            signInRecordService.delete(signInRecordParam);
            return representation(ResultCodeEnum.SUCCESS,signInRecordParam );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

}
