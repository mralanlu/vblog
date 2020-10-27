package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.ImageCalendarDetailParam;
import com.northbund.vblog.pojo.param.ImageCalendarParam;
import com.northbund.vblog.service.ImageCalendarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/imageCalendar")
public class ImageCalendarController extends BaseController{

    @Autowired
    private ImageCalendarService imageCalendarService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/imageCalendarList", method = RequestMethod.POST)
    public JSONObject imageCalendarList(@RequestBody ImageCalendarParam imageCalendarParam) {
        try {
            PageBean pageBean=imageCalendarService.findAllImageCalendarByParam(imageCalendarParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 首页快讯和日历查询
     */
    @RequestMapping(value="/getImageCalendarAndFastNewsForHomepage", method = RequestMethod.POST)
    public JSONObject getImageCalendarAndFastNewsForHomepage() {
        try {
            return representation(ResultCodeEnum.SUCCESS, imageCalendarService.getImageCalendarAndFastNewsForHomepage());
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 添加 imageCalendar
     */
    @RequestMapping(value="/addImageCalendar", method = RequestMethod.POST)
    public JSONObject addImageCalendar(@RequestBody ImageCalendarParam imageCalendarParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, imageCalendarService.addImageCalendar(imageCalendarParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 修改 imageCalendar
     */
    @Transactional
    @RequestMapping(value="/updateImageCalendar", method = RequestMethod.POST)
    public JSONObject updateImageCalendar(@RequestBody  ImageCalendarParam imageCalendarParam) {
        try {
            imageCalendarService.updateImageCalendar(imageCalendarParam);
            return representation(ResultCodeEnum.SUCCESS, imageCalendarParam);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 删除 ImageCalendar
     */
    @RequestMapping(value="/deleteImageCalendar", method = RequestMethod.POST)
    public JSONObject deleteImageCalendar(@RequestBody ImageCalendarParam imageCalendarParam) {
        try {
            imageCalendarService.deleteImageCalendar(imageCalendarParam);
            return representation(ResultCodeEnum.SUCCESS,imageCalendarParam );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/imageCalendarDetailList", method = RequestMethod.POST)
    public JSONObject imageCalendarDetailList(@RequestBody ImageCalendarDetailParam imageCalendarDetailParam) {
        try {
            PageBean pageBean=imageCalendarService.findAllImageCalendarDetailByParam(imageCalendarDetailParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 添加 imageCalendarDetail
     */
    @Transactional
    @RequestMapping(value="/addImageCalendarDetail", method = RequestMethod.POST)
    public JSONObject addImageCalendarDetail(@RequestBody ImageCalendarDetailParam imageCalendarDetailParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS,imageCalendarService.addImageCalendarDetail(imageCalendarDetailParam) );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }



    /**
     * @description: 修改 imageCalendarDetail
     */
    @RequestMapping(value="/updateImageCalendarDetail", method = RequestMethod.POST)
    public JSONObject updateImageCalendarDetail(@RequestBody  ImageCalendarDetailParam imageCalendarDetailParam) {
        try {
            imageCalendarService.updateImageCalendarDetail(imageCalendarDetailParam);
            return representation(ResultCodeEnum.SUCCESS, imageCalendarDetailParam);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 删除 ImageCalendarDetail
     */
    @RequestMapping(value="/deleteImageCalendarDetail", method = RequestMethod.POST)
    public JSONObject deleteImageCalendarDetail(@RequestBody ImageCalendarDetailParam imageCalendarDetailParam) {
        try {
            imageCalendarService.deleteImageCalendarDetail(imageCalendarDetailParam);
            return representation(ResultCodeEnum.SUCCESS,imageCalendarDetailParam );
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

}
