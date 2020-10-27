package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.mapper.FastNewsMapper;
import com.northbund.vblog.pojo.entity.FastNews;
import com.northbund.vblog.pojo.param.FastNewsParam;
import com.northbund.vblog.pojo.vo.FastNewsResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class FastNewsService {

    @Autowired
    private FastNewsMapper fastNewsMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @PagingQuery
    public PageBean findAllByParam(FastNewsParam  fastNewsParam) {
        List<FastNewsResult> fastNewsResults = fastNewsMapper.findAllByParam(fastNewsParam);
        convertUploadFile(fastNewsResults);
        return new PageBean(fastNewsResults);
    }

    private List<FastNewsResult> convertUploadFile(List<FastNewsResult> fastNewsResults){
        if(!CollectionUtils.isEmpty(fastNewsResults)){
            for (FastNewsResult fastNewsResult : fastNewsResults){
                fastNewsResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(fastNewsResult.getId(), AttachmentTypeEnum.FAST_NEWS.getCode()));
            }
        }
        return fastNewsResults;
    }

    public Long add(FastNewsParam fastNewsParam){
        FastNews fastNews = new FastNews();
        BeanUtilExtend.copyProperties( fastNewsParam,fastNews,true,null);
        fastNews.setCreateTime(new Date());
        fastNews.setUpdateTime(new Date());
        fastNewsMapper.insert(fastNews);
        if(!CollectionUtils.isEmpty(fastNewsParam.getCommonFileUploads())){
            commonFileUploadService.save(fastNewsParam.getCommonFileUploads(),fastNews.getId(), AttachmentTypeEnum.FAST_NEWS.getCode());
        }
        return fastNews.getId();
    }

    public void update(FastNewsParam fastNewsParam){
        FastNews fastNews = new FastNews();
        BeanUtilExtend.copyProperties( fastNewsParam,fastNews,true,null);
        fastNews.setCreateTime(new Date());
        fastNews.setUpdateTime(new Date());
        fastNewsMapper.update(fastNews);
        if(!CollectionUtils.isEmpty(fastNewsParam.getCommonFileUploads())){
            commonFileUploadService.save(fastNewsParam.getCommonFileUploads(),fastNews.getId(), AttachmentTypeEnum.FAST_NEWS.getCode());
        }
    }
    public void delete(FastNewsParam fastNewsParam){
        fastNewsMapper.delete(fastNewsParam.getId());
    }
}
