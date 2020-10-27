package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.ExchangeRecordMapper;
import com.northbund.vblog.mapper.ScoreShopMapper;
import com.northbund.vblog.mapper.UserMapper;
import com.northbund.vblog.pojo.entity.ExchangeRecord;
import com.northbund.vblog.pojo.entity.ScoreShop;
import com.northbund.vblog.pojo.entity.User;
import com.northbund.vblog.pojo.param.ExchangeRecordParam;
import com.northbund.vblog.pojo.vo.ExchangeRecordResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ExchangeRecordService {

    @Autowired
    private ExchangeRecordMapper exchangeRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ScoreShopMapper scoreShopMapper;

    @PagingQuery
    public PageBean findAllByParam(ExchangeRecordParam exchangeRecordParam) {
        List<ExchangeRecordResult> exchangeRecordResults = exchangeRecordMapper.findAllByParam(exchangeRecordParam);
        return new PageBean(exchangeRecordResults);
    }

    /**
     * 兑换积分商品
     * @param exchangeRecordParam
     * @return
     */
    @Transactional
    public void exchangeScoreGoods(ExchangeRecordParam exchangeRecordParam){
        if(null == exchangeRecordParam.getUserId() || null ==exchangeRecordParam.getScoreGoodsId()){
            throw new CommonException(ResultCodeEnum.SCORE_OR_USER_PARAM_ERROR);
        }
        User user = userMapper.findById(exchangeRecordParam.getUserId());
        ScoreShop scoreShop = scoreShopMapper.findById(exchangeRecordParam.getScoreGoodsId());
        if(null == user || null == scoreShop){
            throw new CommonException(ResultCodeEnum.SCORE_OR_USER__NOT_EXIST);
        }
        if(null==user.getScore() || user.getScore()<exchangeRecordParam.getRealScore()){
            throw new CommonException(ResultCodeEnum.SCORE_NOT_ENOUGH);
        }
        //扣除用户积分
        user.setScore(user.getScore()-exchangeRecordParam.getRealScore());
        userMapper.update(user);
        //创建兑换记录
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        BeanUtilExtend.copyProperties( exchangeRecordParam,exchangeRecord,true,null);
        exchangeRecord.setExchangeTime(new Date());
        exchangeRecordMapper.insert(exchangeRecord);

    }

    public void update(ExchangeRecordParam exchangeRecordParam){
        ExchangeRecord exchangeRecord = new ExchangeRecord();
        BeanUtilExtend.copyProperties( exchangeRecordParam,exchangeRecord,true,null);
        exchangeRecordMapper.update(exchangeRecord);
    }
    public void delete(ExchangeRecordParam exchangeRecordParam){
        exchangeRecordMapper.delete(exchangeRecordParam.getId());
    }
}
