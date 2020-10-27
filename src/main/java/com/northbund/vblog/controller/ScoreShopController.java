package com.northbund.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.ExchangeRecordParam;
import com.northbund.vblog.pojo.param.ScoreShopParam;
import com.northbund.vblog.service.ExchangeRecordService;
import com.northbund.vblog.service.ScoreShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/scoreShop")
public class ScoreShopController extends BaseController{

    @Autowired
    private ScoreShopService scoreShopService;

    @Autowired
    private ExchangeRecordService exchangeRecordService;

    /**
     * @description: 分页查询积分商品
     */
    @RequestMapping(value="/listScoreGoods", method = RequestMethod.POST)
    public JSONObject listScoreGoods(@RequestBody ScoreShopParam scoreShopParam) {
        try {
            PageBean pageBean=scoreShopService.findAllByParam(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

    /**
     * @description:  添加积分商品
     */
    @RequestMapping(value="/addScoreGoods", method = RequestMethod.POST)
    public JSONObject addScoreGoods(@RequestBody ScoreShopParam scoreShopParam) {
        try {
            scoreShopService.add(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.ADD_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 修改积分商品
     */
    @RequestMapping(value="/updateScoreGoods", method = RequestMethod.POST)
    public JSONObject updateScoreGoods(@RequestBody  ScoreShopParam scoreShopParam) {
        try {
            scoreShopService.update(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 积分商品上架
     */
    @RequestMapping(value="/upScoreGoods", method = RequestMethod.POST)
    public JSONObject upScoreGoods(@RequestBody  ScoreShopParam scoreShopParam) {
        try {
            scoreShopService.upScoreGoods(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 积分商品下架
     */
    @RequestMapping(value="/downScoreGoods", method = RequestMethod.POST)
    public JSONObject downScoreGoods(@RequestBody  ScoreShopParam scoreShopParam) {
        try {
            scoreShopService.downScoreGoods(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.UPDATE_ERROR,e.getMessage());
        }
    }


    /**
     * @description: 删除积分商品
     */
    @RequestMapping(value="/deleteScoreGoods", method = RequestMethod.POST)
    public JSONObject deleteSignInRecord(@RequestBody ScoreShopParam scoreShopParam) {
        try {
            scoreShopService.delete(scoreShopParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.DELETE_ERROR,e.getMessage());
        }
    }

    /**
     * @description: 兑换积分商品
     */
    @RequestMapping(value="/exchangeScoreGoods", method = RequestMethod.POST)
    public JSONObject addExchangeRecord(@RequestBody ExchangeRecordParam exchangeRecordParam) {
        try {
            exchangeRecordService.exchangeScoreGoods(exchangeRecordParam);
            return representation(ResultCodeEnum.SUCCESS);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.ERROR_EXCHANGE_SCORE_GOODS,e.getMessage());
        }
    }

    /**
     * @description: 分页查询兑换记录
     */
    @RequestMapping(value="/listExchangeRecord", method = RequestMethod.POST)
    public JSONObject listExchangeRecord(@RequestBody ExchangeRecordParam exchangeRecordParam) {
        try {
            PageBean pageBean=exchangeRecordService.findAllByParam(exchangeRecordParam);
            return representation(ResultCodeEnum.SUCCESS, pageBean);
        } catch (CommonException e) {
            return representation(e.getMessageInfo());
        } catch (Exception e) {
            log.error("--error--", e);
            return representation(ResultCodeEnum.QUERY_ERROR,e.getMessage());
        }
    }

}
