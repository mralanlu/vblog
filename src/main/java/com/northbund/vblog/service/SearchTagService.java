package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.mapper.SearchTagMapper;
import com.northbund.vblog.pojo.entity.SearchTag;
import com.northbund.vblog.pojo.param.SearchTagParam;
import com.northbund.vblog.pojo.vo.FastNewsResult;
import com.northbund.vblog.pojo.vo.ImageCalendarResult;
import com.northbund.vblog.pojo.vo.SearchTagResult;
import com.northbund.vblog.pojo.vo.WorksCollectionResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchTagService {

    @Autowired
    private SearchTagMapper searchTagMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;


    @PagingQuery
    public PageBean findAllByParam(SearchTagParam searchTagParam) {
        List<SearchTagResult> searchTagResults = searchTagMapper.findAllByParam(searchTagParam);
        return new PageBean(searchTagResults);
    }


    public Long add(SearchTagParam searchTagParam){
        SearchTag searchTag = new SearchTag();
        BeanUtilExtend.copyProperties( searchTagParam,searchTag,true,null);
        searchTagMapper.insert(searchTag);
        return searchTag.getId();
    }

    public void update(SearchTagParam searchTagParam){
        SearchTag searchTag = new SearchTag();
        BeanUtilExtend.copyProperties( searchTagParam,searchTag,true,null);
        searchTagMapper.update(searchTag);
    }

    public void delete(SearchTagParam searchTagParam){
        searchTagMapper.delete(searchTagParam.getId());
    }

    public Map search(String searchTag){
        Map result = new HashMap();
        //搜索影像部落 type 1
        List<WorksCollectionResult> worksCollectionResults = searchTagMapper.getSearchResult1(searchTag);
        convertUploadFile1(worksCollectionResults);
        result.put("1",worksCollectionResults);
        //搜索快讯 type 2
        List<FastNewsResult> fastNewsResults = searchTagMapper.getSearchResult2(searchTag);
        convertUploadFile2(fastNewsResults);
        result.put("2",fastNewsResults);
        //搜索影像日历 type 3
        List<ImageCalendarResult> imageCalendarResults = searchTagMapper.getSearchResult3(searchTag);
        convertUploadFile3(imageCalendarResults);
        result.put("3",imageCalendarResults);
        return result;
    }

    private List<WorksCollectionResult> convertUploadFile1(List<WorksCollectionResult> worksCollectionResults){
        if(!CollectionUtils.isEmpty(worksCollectionResults)){
            for (WorksCollectionResult worksCollectionResult : worksCollectionResults){
                worksCollectionResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(worksCollectionResult.getId(), AttachmentTypeEnum.WORKS.getCode()));
            }
        }
        return worksCollectionResults;
    }

    private List<FastNewsResult> convertUploadFile2(List<FastNewsResult> fastNewsResults){
        if(!CollectionUtils.isEmpty(fastNewsResults)){
            for (FastNewsResult fastNewsResult : fastNewsResults){
                fastNewsResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(fastNewsResult.getId(), AttachmentTypeEnum.FAST_NEWS.getCode()));
            }
        }
        return fastNewsResults;
    }

    private List<ImageCalendarResult> convertUploadFile3(List<ImageCalendarResult> imageCalendarResults){
        if(!CollectionUtils.isEmpty(imageCalendarResults)){
            for (ImageCalendarResult imageCalendarResult : imageCalendarResults){
                imageCalendarResult.setThumbnailResults(commonFileUploadService.getByRelIdAndAttachmentType(imageCalendarResult.getId(), AttachmentTypeEnum.THUMBNAIL.getCode()));
                imageCalendarResult.setPosterResults(commonFileUploadService.getByRelIdAndAttachmentType(imageCalendarResult.getId(), AttachmentTypeEnum.POSTER.getCode()));
            }
        }
        return imageCalendarResults;
    }
}
