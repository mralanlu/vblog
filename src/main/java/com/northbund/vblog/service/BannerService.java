package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.mapper.BannerMapper;
import com.northbund.vblog.pojo.entity.Banner;
import com.northbund.vblog.pojo.param.BannerParam;
import com.northbund.vblog.pojo.vo.BannerResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @PagingQuery
    public PageBean findAllByParam(BannerParam bannerParam) {
        List<BannerResult> bannerResults= bannerMapper.findAllByParam(bannerParam);
        convertUploadFile(bannerResults);
        return new PageBean(bannerResults);
    }

    private List<BannerResult> convertUploadFile(List<BannerResult> bannerResults){
        if(!CollectionUtils.isEmpty(bannerResults)){
            for (BannerResult bannerResult : bannerResults){
                bannerResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(bannerResult.getId(), AttachmentTypeEnum.BANNER.getCode()));
            }
        }
        return bannerResults;
    }

    public Long add(BannerParam bannerParam){
        Banner banner = new Banner();
        BeanUtilExtend.copyProperties( bannerParam,banner,true,null);
        banner.setCreateTime(new Date());
        banner.setUpdateTime(new Date());
        bannerMapper.insert(banner);
        if(!CollectionUtils.isEmpty(bannerParam.getCommonFileUploads())){
            commonFileUploadService.save(bannerParam.getCommonFileUploads(),banner.getId(), AttachmentTypeEnum.BANNER
                    .getCode());
        }
        return banner.getId();
    }

    public void update(BannerParam bannerParam){
        Banner banner = new Banner();
        BeanUtilExtend.copyProperties( bannerParam,banner,true,null);
        banner.setUpdateTime(new Date());
        bannerMapper.update(banner);
        if(!CollectionUtils.isEmpty(bannerParam.getCommonFileUploads())){
            commonFileUploadService.save(bannerParam.getCommonFileUploads(),banner.getId(), AttachmentTypeEnum.BANNER.getCode());
        }
    }
    public void delete(BannerParam bannerParam){
        bannerMapper.delete(bannerParam.getId());
    }
}
