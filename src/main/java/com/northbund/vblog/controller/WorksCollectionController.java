package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.WorksCollectionParam;
import com.northbund.vblog.pojo.param.WorksLikesRecordParam;
import com.northbund.vblog.service.WorksCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/worksCollection")
public class WorksCollectionController extends BaseController{

    @Autowired
    private WorksCollectionService worksCollectionService;


    /**
     * @description: 分页查询
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    public JSONObject listWorksCollection(@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            PageBean pageBean=worksCollectionService.findAllByParam(worksCollectionParam);
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
    public JSONObject addWorksCollection(@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS, worksCollectionService.add(worksCollectionParam));
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
    public JSONObject updateWorksCollection(@RequestBody  WorksCollectionParam worksCollectionParam) {
        try {
            worksCollectionService.update(worksCollectionParam);
            return representation(ResultCodeEnum.SUCCESS);
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
    public JSONObject deleteWorksCollection(@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            worksCollectionService.delete(worksCollectionParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 审核成功
     */
    @RequestMapping(value="/reviewSuccess", method = RequestMethod.POST)
    public JSONObject reviewSuccess(@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            worksCollectionService.reviewSuccess(worksCollectionParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 审核失败
     */
    @RequestMapping(value="/reviewFailure", method = RequestMethod.POST)
    public JSONObject reviewFailure(@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            worksCollectionService.reviewFailure(worksCollectionParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 点赞
     */
    @RequestMapping(value="/likes", method = RequestMethod.POST)
    public JSONObject likes (@RequestBody WorksLikesRecordParam worksLikesRecordParam) {
        try {
            worksCollectionService.likes(worksLikesRecordParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 取消点赞
     */
    @RequestMapping(value="/dislikes", method = RequestMethod.POST)
    public JSONObject dislikes (@RequestBody WorksLikesRecordParam worksLikesRecordParam) {
        try {
            worksCollectionService.dislikes(worksLikesRecordParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 获取热门作品(按季度比较点赞数，取前三)
     */
    @RequestMapping(value="/getRecommendedWorks", method = RequestMethod.POST)
    public JSONObject getRecommendedWorks (@RequestBody WorksCollectionParam worksCollectionParam) {
        try {
            return representation(ResultCodeEnum.SUCCESS,worksCollectionService.getRecommendedWorks(worksCollectionParam));
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

}
