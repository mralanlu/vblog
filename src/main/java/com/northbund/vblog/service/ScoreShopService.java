package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.ExchangeRecordMapper;
import com.northbund.vblog.mapper.ScoreShopMapper;
import com.northbund.vblog.pojo.entity.ScoreShop;
import com.northbund.vblog.pojo.param.ExchangeRecordParam;
import com.northbund.vblog.pojo.param.ScoreShopParam;
import com.northbund.vblog.pojo.vo.ExchangeRecordResult;
import com.northbund.vblog.pojo.vo.ScoreShopResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class ScoreShopService {

    @Autowired
    private ScoreShopMapper scoreShopMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @Autowired
    private ExchangeRecordMapper exchangeRecordMapper;


    @PagingQuery
    public PageBean findAllByParam(ScoreShopParam scoreShopParam) {
        List<ScoreShopResult> scoreShopResults = scoreShopMapper.findAllByParam(scoreShopParam);
        convertUploadFile(scoreShopResults);
        return new PageBean(scoreShopResults);
    }

    private List<ScoreShopResult> convertUploadFile(List<ScoreShopResult> scoreShopResults){
        if(!CollectionUtils.isEmpty(scoreShopResults)){
            for (ScoreShopResult scoreShopResult : scoreShopResults){
                scoreShopResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(scoreShopResult.getId(), AttachmentTypeEnum.SCORE_GOODS.getCode()));
            }
        }
        return scoreShopResults;
    }


    @Transactional
    public Long add(ScoreShopParam scoreShopParam){
        ScoreShop scoreShop = new ScoreShop();
        BeanUtilExtend.copyProperties( scoreShopParam,scoreShop,true,null);
        scoreShop.setCreateTime(new Date());
        scoreShop.setUpdateTime(new Date());
        scoreShopMapper.insert(scoreShop);
        if(!CollectionUtils.isEmpty(scoreShopParam.getCommonFileUploads())){
            commonFileUploadService.save(scoreShopParam.getCommonFileUploads(),scoreShop.getId(), AttachmentTypeEnum.SCORE_GOODS.getCode());
        }
        return scoreShop.getId();
    }


    public void update(ScoreShopParam scoreShopParam){
        ScoreShop scoreShop = new ScoreShop();
        BeanUtilExtend.copyProperties( scoreShopParam,scoreShop,true,null);
        scoreShop.setUpdateTime(new Date());
        scoreShopMapper.update(scoreShop);
        if(!CollectionUtils.isEmpty(scoreShopParam.getCommonFileUploads())){
            commonFileUploadService.save(scoreShopParam.getCommonFileUploads(),scoreShop.getId(), AttachmentTypeEnum.SCORE_GOODS.getCode());
        }
    }

    public void upScoreGoods(ScoreShopParam scoreShopParam){
        if(null == scoreShopParam.getId()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
        ScoreShop scoreShop = new ScoreShop();
        BeanUtilExtend.copyProperties( scoreShopParam,scoreShop,true,null);
        scoreShop.setUpdateTime(new Date());
        scoreShop.setScoreGoodsStatus(1);
        scoreShopMapper.update(scoreShop);
    }

    public void downScoreGoods(ScoreShopParam scoreShopParam){
        if(null == scoreShopParam.getId()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
        ScoreShop scoreShop = new ScoreShop();
        BeanUtilExtend.copyProperties( scoreShopParam,scoreShop,true,null);
        scoreShop.setUpdateTime(new Date());
        scoreShop.setScoreGoodsStatus(0);
        scoreShopMapper.update(scoreShop);
    }

    public void delete(ScoreShopParam scoreShopParam){
        ExchangeRecordParam exchangeRecordParam = new ExchangeRecordParam();
        exchangeRecordParam.setScoreGoodsId(scoreShopParam.getId());
        List<ExchangeRecordResult> exchangeRecordResults = exchangeRecordMapper.findAllByParam(exchangeRecordParam);
        if(exchangeRecordResults.size()>0){
            throw new CommonException(ResultCodeEnum.SCORE_DELETE_ERROR_HAS_EXCHANGE_RECORD);
        }else {
            scoreShopMapper.delete(scoreShopParam.getId());
        }
    }
}
