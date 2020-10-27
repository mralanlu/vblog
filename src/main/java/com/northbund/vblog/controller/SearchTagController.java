package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.SearchTagParam;
import com.northbund.vblog.service.SearchTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/searchTag")
public class SearchTagController extends BaseController{

    @Autowired
    private SearchTagService searchTagService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject list(@RequestBody SearchTagParam searchTagParam) {
        try {
            PageBean pageBean=searchTagService.findAllByParam(searchTagParam);
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
    public JSONObject addFastNews(@RequestBody SearchTagParam searchTagPara) {
        try {
            return representation(ResultCodeEnum.SUCCESS, searchTagService.add(searchTagPara));
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
    public JSONObject updateFastNews(@RequestBody  SearchTagParam searchTagPara) {
        try {
            searchTagService.update(searchTagPara);
            return representation(ResultCodeEnum.SUCCESS, searchTagPara);
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
    public JSONObject delete(@RequestBody SearchTagParam searchTagPara) {
        try {
            searchTagService.delete(searchTagPara);
            return representation(ResultCodeEnum.SUCCESS,searchTagPara );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 搜索
     */
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public JSONObject search(@RequestBody SearchTagParam searchTagParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS,searchTagService.search(searchTagParam.getName()) );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

}
