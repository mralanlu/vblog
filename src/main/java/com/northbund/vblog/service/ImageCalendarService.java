package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.FastNewsMapper;
import com.northbund.vblog.mapper.ImageCalendarMapper;
import com.northbund.vblog.pojo.entity.ImageCalendar;
import com.northbund.vblog.pojo.entity.ImageCalendarDetail;
import com.northbund.vblog.pojo.param.FastNewsParam;
import com.northbund.vblog.pojo.param.ImageCalendarDetailParam;
import com.northbund.vblog.pojo.param.ImageCalendarParam;
import com.northbund.vblog.pojo.vo.FastNewsResult;
import com.northbund.vblog.pojo.vo.ImageCalendarDetailResult;
import com.northbund.vblog.pojo.vo.ImageCalendarResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ImageCalendarService {

    @Autowired
    private ImageCalendarMapper imageCalendarMapper;

    @Autowired
    private FastNewsMapper fastNewsMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;


    public Map<String,Object> getImageCalendarAndFastNewsForHomepage(){
        Map<String ,Object> result = new HashMap<>();
        List<FastNewsResult> fastNewsResults = fastNewsMapper.getLastDisplayDateFastNews(new Date());
        if(!CollectionUtils.isEmpty(fastNewsResults)){
            FastNewsParam fastNewsParam = new FastNewsParam();
            fastNewsParam.setDisplayTime(fastNewsResults.get(0).getDisplayTime());
            fastNewsResults = fastNewsMapper.findAllByParam(fastNewsParam);
        }
        List<ImageCalendarResult> imageCalendarResults = imageCalendarMapper.getLastDisplayDateImageCalendar(new Date());
        result.put("fastNews",convertFastNewsUploadFile(fastNewsResults));
        result.put("imageCalendar",convertImageCalendarUploadFile(imageCalendarResults));
        return result;
    }

    private List<FastNewsResult> convertFastNewsUploadFile(List<FastNewsResult> fastNewsResults){
        if(!CollectionUtils.isEmpty(fastNewsResults)){
            for (FastNewsResult fastNewsResult : fastNewsResults){
                fastNewsResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(fastNewsResult.getId(), AttachmentTypeEnum.FAST_NEWS.getCode()));
            }
        }
        return fastNewsResults;
    }

    @PagingQuery
    public PageBean findAllImageCalendarByParam(ImageCalendarParam imageCalendarParam) {
        List<ImageCalendarResult> imageCalendarResults = imageCalendarMapper.findAllImageCalendarByParam(imageCalendarParam);
        /*if(!CollectionUtils.isEmpty(imageCalendarResults)){
            for(ImageCalendarResult imageCalendarResult:imageCalendarResults){
                ImageCalendarDetailParam imageCalendarDetailParam = new ImageCalendarDetailParam();
                imageCalendarDetailParam.setImageCalendarId(imageCalendarResult.getId());
                List<ImageCalendarDetailResult> imageCalendarDetailResults = imageCalendarMapper.findAllImageCalendarDetailByParam(imageCalendarDetailParam);
                convertUploadFile(imageCalendarDetailResults);
                imageCalendarResult.setImageCalendarDetailResults(imageCalendarDetailResults);
            }
        }*/
        convertImageCalendarUploadFile(imageCalendarResults);
        return new PageBean(imageCalendarResults);
    }

    private List<ImageCalendarResult> convertImageCalendarUploadFile(List<ImageCalendarResult> imageCalendarResults){
        if(!CollectionUtils.isEmpty(imageCalendarResults)){
            for (ImageCalendarResult imageCalendarResult : imageCalendarResults){
                imageCalendarResult.setThumbnailResults(commonFileUploadService.getByRelIdAndAttachmentType(imageCalendarResult.getId(), AttachmentTypeEnum.THUMBNAIL.getCode()));
                imageCalendarResult.setPosterResults(commonFileUploadService.getByRelIdAndAttachmentType(imageCalendarResult.getId(), AttachmentTypeEnum.POSTER.getCode()));
            }
        }
        return imageCalendarResults;
    }


    @Transactional
    public Long addImageCalendar(ImageCalendarParam imageCalendarParam){

        if(CollectionUtils.isEmpty(imageCalendarParam.getThumbnail())){
            throw new CommonException(ResultCodeEnum.THUMBNAIL_CANNOT_EMPTY);
        }
        if(CollectionUtils.isEmpty(imageCalendarParam.getPoster())){
            throw new CommonException(ResultCodeEnum.POSTER_CANNOT_EMPTY);
        }
        ImageCalendarParam oldImageCalendarParam = new ImageCalendarParam();
        oldImageCalendarParam.setDisplayTime(imageCalendarParam.getDisplayTime());
        List<ImageCalendarResult> imageCalendarResults = imageCalendarMapper.findAllImageCalendarByParam(oldImageCalendarParam);
        if(imageCalendarResults.size()>0){
            throw new CommonException(ResultCodeEnum.IMAGE_CALENDAR_EXISTS);
        }
        ImageCalendar imageCalendar = new ImageCalendar();
        BeanUtilExtend.copyProperties( imageCalendarParam,imageCalendar,true,null);
        imageCalendar.setCreateTime(new Date());
        imageCalendar.setUpdateTime(new Date());
        imageCalendarMapper.insertImageCalendar(imageCalendar);
        if(!CollectionUtils.isEmpty(imageCalendarParam.getThumbnail())){
            commonFileUploadService.save(imageCalendarParam.getThumbnail(),imageCalendar.getId(), AttachmentTypeEnum.THUMBNAIL.getCode());
        }
        if(!CollectionUtils.isEmpty(imageCalendarParam.getPoster())){
            commonFileUploadService.save(imageCalendarParam.getPoster(),imageCalendar.getId(), AttachmentTypeEnum.POSTER.getCode());
        }
        return imageCalendar.getId();
    }

    @Transactional
    public void updateImageCalendar(ImageCalendarParam imageCalendarParam){

        if(CollectionUtils.isEmpty(imageCalendarParam.getThumbnail())){
            throw new CommonException(ResultCodeEnum.THUMBNAIL_CANNOT_EMPTY);
        }
        if(CollectionUtils.isEmpty(imageCalendarParam.getPoster())){
            throw new CommonException(ResultCodeEnum.POSTER_CANNOT_EMPTY);
        }
        ImageCalendar imageCalendar = new ImageCalendar();
        BeanUtilExtend.copyProperties( imageCalendarParam,imageCalendar,true,null);
        imageCalendar.setUpdateTime(new Date());
        imageCalendarMapper.updateImageCalendar(imageCalendar);
        if(!CollectionUtils.isEmpty(imageCalendarParam.getThumbnail())){
            commonFileUploadService.save(imageCalendarParam.getThumbnail(),imageCalendar.getId(), AttachmentTypeEnum.THUMBNAIL.getCode());
        }
        if(!CollectionUtils.isEmpty(imageCalendarParam.getPoster())){
            commonFileUploadService.save(imageCalendarParam.getPoster(),imageCalendar.getId(), AttachmentTypeEnum.POSTER.getCode());
        }
    }

    public void deleteImageCalendar(ImageCalendarParam imageCalendarParam){
        imageCalendarMapper.deleteImageCalendar(imageCalendarParam.getId());
    }

    @PagingQuery
    public PageBean findAllImageCalendarDetailByParam(ImageCalendarDetailParam imageCalendarDetailParam) {
        List<ImageCalendarDetailResult> imageCalendarDetailResults = imageCalendarMapper.findAllImageCalendarDetailByParam(imageCalendarDetailParam);
        convertImageCalendarDetailUploadFile(imageCalendarDetailResults);
        return new PageBean(imageCalendarDetailResults);
    }

    private List<ImageCalendarDetailResult> convertImageCalendarDetailUploadFile(List<ImageCalendarDetailResult> imageCalendarDetailResults){
        if(!CollectionUtils.isEmpty(imageCalendarDetailResults)){
            for (ImageCalendarDetailResult imageCalendarDetailResult : imageCalendarDetailResults){
                imageCalendarDetailResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(imageCalendarDetailResult.getId(), AttachmentTypeEnum.CALENDAR.getCode()));
            }
        }
        return imageCalendarDetailResults;
    }

    @Transactional
    public Long addImageCalendarDetail(ImageCalendarDetailParam imageCalendarDetailParam){
        ImageCalendarDetail imageCalendarDetail = new ImageCalendarDetail();
        BeanUtilExtend.copyProperties( imageCalendarDetailParam,imageCalendarDetail,true,null);
        imageCalendarDetail.setCreateTime(new Date());
        imageCalendarDetail.setUpdateTime(new Date());
        imageCalendarMapper.insertImageCalendarDetail(imageCalendarDetail);
        if(!CollectionUtils.isEmpty(imageCalendarDetailParam.getCommonFileUploads())){
            commonFileUploadService.save(imageCalendarDetailParam.getCommonFileUploads(),imageCalendarDetail.getId(), AttachmentTypeEnum.CALENDAR.getCode());
        }
        return imageCalendarDetail.getId();
    }


    public void updateImageCalendarDetail(ImageCalendarDetailParam imageCalendarDetailParam){
        ImageCalendarDetail imageCalendarDetail = new ImageCalendarDetail();
        BeanUtilExtend.copyProperties( imageCalendarDetailParam,imageCalendarDetail,true,null);
        imageCalendarDetail.setUpdateTime(new Date());
        imageCalendarMapper.updateImageCalendarDetail(imageCalendarDetail);
    }

    public void deleteImageCalendarDetail(ImageCalendarDetailParam imageCalendarDetailParam){
        imageCalendarMapper.deleteImageCalendarDetail(imageCalendarDetailParam.getId());
    }
}
