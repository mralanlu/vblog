package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.FastNewsParam;
import com.northbund.vblog.service.FastNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/fastNews")
public class FastNewsController extends BaseController{

    @Autowired
    private FastNewsService fastNewsService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject listFastNews(@RequestBody FastNewsParam fastNewsParam) {
        try {
            PageBean pageBean=fastNewsService.findAllByParam(fastNewsParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 添加
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public JSONObject addFastNews(@RequestBody FastNewsParam fastNewsParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, fastNewsService.add(fastNewsParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.ADD_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 修改
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public JSONObject updateFastNews(@RequestBody  FastNewsParam fastNewsParam) {
        try {
            fastNewsService.update(fastNewsParam);
            return representation(ResultCodeEnum.SUCCESS, fastNewsParam);
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
    public JSONObject deleteFastNews(@RequestBody FastNewsParam fastNewsParam) {
        try {
            fastNewsService.delete(fastNewsParam);
            return representation(ResultCodeEnum.SUCCESS,fastNewsParam );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

}
