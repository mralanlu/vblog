package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.UserParam;
import com.northbund.vblog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{


    @Autowired
    private UserService userService;


    /**
     * @description: 列表
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject list(@RequestBody UserParam userParam) {
        try {

            return representation(ResultCodeEnum.SUCCESS, userService.getUserInfoList(userParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 注册
     */
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public JSONObject register(@RequestBody UserParam userParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, userService.register(userParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.REGISTER_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 增加管理员
     */
    @RequestMapping(value="/addAdmin", method = RequestMethod.POST)
    public JSONObject addAdmin(@RequestBody UserParam userParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, userService.addAdmin(userParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.ADD_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 删除
     */
    @RequestMapping(value="/delete", method = RequestMethod.POST)
    public JSONObject delete(@RequestBody UserParam userParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, userService.delete(userParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 获取短信验证码
     */
    @RequestMapping(value="/sendVerifyCode", method = RequestMethod.POST)
    public JSONObject sendVerifyCode(@RequestBody UserParam userParam) {
        try {
            userService.getVerifyCode(userParam.getPhone());
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.GET_VERIFICATION_UNKNOWN_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 获取用户详情
     */
    @RequestMapping(value="/getUserInfo", method = RequestMethod.POST)
    public JSONObject getUserInfo(@RequestBody UserParam userParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, userService.getUserInfo(userParam.getId()));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 更新用户详情
     */
    @RequestMapping(value="/updateUserInfo", method = RequestMethod.POST)
    public JSONObject updateUserInfo(@RequestBody UserParam userParam) {
        try {
            userService.updateUserInfo(userParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

}
