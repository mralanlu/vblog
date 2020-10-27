package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.BannerParam;
import com.northbund.vblog.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/banner")
public class BannerController extends BaseController{

    @Autowired
    private BannerService bannerService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject list(@RequestBody BannerParam bannerParam) {
        try {
            PageBean pageBean=bannerService.findAllByParam(bannerParam);
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
    public JSONObject add(@RequestBody BannerParam bannerParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, bannerService.add(bannerParam));
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
    public JSONObject update(@RequestBody  BannerParam bannerParam) {
        try {
            bannerService.update(bannerParam);
            return representation(ResultCodeEnum.SUCCESS, bannerParam);
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
    public JSONObject delete(@RequestBody BannerParam bannerParam) {
        try {
            bannerService.delete(bannerParam);
            return representation(ResultCodeEnum.SUCCESS,bannerParam );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

}
