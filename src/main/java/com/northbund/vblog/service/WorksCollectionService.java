package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.AttachmentTypeEnum;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.WorksCollectionMapper;
import com.northbund.vblog.pojo.entity.WorksCollection;
import com.northbund.vblog.pojo.entity.WorksLikesRecord;
import com.northbund.vblog.pojo.param.WorksCollectionParam;
import com.northbund.vblog.pojo.param.WorksLikesRecordParam;
import com.northbund.vblog.pojo.vo.WorksCollectionResult;
import com.northbund.vblog.pojo.vo.WorksLikesRecordResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import com.northbund.vblog.utils.DateUtil;
import com.northbund.vblog.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static com.northbund.vblog.common.constant.Constant.VERIFY_CODE;

@Service
public class WorksCollectionService {

    @Autowired
    private WorksCollectionMapper worksCollectionMapper;

    @Autowired
    private CommonFileUploadService commonFileUploadService;

    @Autowired
    private RedisUtils redisUtils;

    @PagingQuery
    public PageBean findAllByParam(WorksCollectionParam worksCollectionParam) {
        List<WorksCollectionResult> worksCollectionResults = worksCollectionMapper.findAllByParam(worksCollectionParam);
        for (WorksCollectionResult worksCollectionResult : worksCollectionResults){
            WorksLikesRecordParam worksLikesRecordParam = new WorksLikesRecordParam();
            worksLikesRecordParam.setWorksId(worksCollectionResult.getId());
            worksLikesRecordParam.setUserId(worksCollectionParam.getCurrentUserId());
            List<WorksLikesRecordResult> worksLikesRecordResults = worksCollectionMapper.getLikesRecord(worksLikesRecordParam);
            if(!CollectionUtils.isEmpty(worksLikesRecordResults)){
                worksCollectionResult.setCurrentUserLikesStatus(worksLikesRecordResults.get(0).getClickStatus());
            }
        }
        convertUploadFile(worksCollectionResults);
        return new PageBean(worksCollectionResults);
    }

    private List<WorksCollectionResult> convertUploadFile(List<WorksCollectionResult> worksCollectionResults){
        if(!CollectionUtils.isEmpty(worksCollectionResults)){
            for (WorksCollectionResult worksCollectionResult : worksCollectionResults){
                worksCollectionResult.setCommonFileUploadResults(commonFileUploadService.getByRelIdAndAttachmentType(worksCollectionResult.getId(), AttachmentTypeEnum.WORKS.getCode()));
            }
        }
        return worksCollectionResults;
    }

    public Long add(WorksCollectionParam worksCollectionParam){
        String redisVerifyCode = redisUtils.get(VERIFY_CODE+worksCollectionParam.getPhone());
        if(null == redisVerifyCode){
            throw new CommonException(ResultCodeEnum.VERIFICATION_OUT_OF_TIME);
        }
        if(redisVerifyCode.equals(worksCollectionParam.getVerifyCode())){
            WorksCollection worksCollection = new WorksCollection();
            BeanUtilExtend.copyProperties( worksCollectionParam,worksCollection,true,null);
            worksCollection.setCreateTime(new Date());
            worksCollection.setWorksStatus(0);
            worksCollectionMapper.insert(worksCollection);
            if(!CollectionUtils.isEmpty(worksCollectionParam.getCommonFileUploads())){
                commonFileUploadService.save(worksCollectionParam.getCommonFileUploads(),worksCollection.getId(), AttachmentTypeEnum.WORKS.getCode());
            }
            return worksCollection.getId();
        }else {
           throw new CommonException(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
    }

    /**
     * 审核成功
     * @param worksCollectionParam
     */
    public void reviewSuccess(WorksCollectionParam worksCollectionParam){
        WorksCollection worksCollection = new WorksCollection();
        worksCollection.setId(worksCollectionParam.getId());
        worksCollection.setWorksStatus(1);
        worksCollectionMapper.update(worksCollection);
    }

    /**
     * 审核失败
     * @param worksCollectionParam
     */
    public void reviewFailure(WorksCollectionParam worksCollectionParam){
        WorksCollection worksCollection = new WorksCollection();
        worksCollection.setId(worksCollectionParam.getId());
        worksCollection.setWorksStatus(2);
        worksCollectionMapper.update(worksCollection);
    }

    /**
     *修改作品
     * @param worksCollectionParam
     */
    public void update(WorksCollectionParam worksCollectionParam){
        WorksCollection worksCollection = new WorksCollection();
        BeanUtilExtend.copyProperties( worksCollectionParam,worksCollection,true,null);
        worksCollection.setWorksStatus(0);
        worksCollectionMapper.update(worksCollection);
        if(!CollectionUtils.isEmpty(worksCollectionParam.getCommonFileUploads())){
            commonFileUploadService.save(worksCollectionParam.getCommonFileUploads(),worksCollection.getId(), AttachmentTypeEnum.WORKS.getCode());
        }
    }

    /**
     *删除作品
     * @param worksCollectionParam
     */
    public void delete(WorksCollectionParam worksCollectionParam){
        worksCollectionMapper.delete(worksCollectionParam.getId());
    }

    /**
     * 点赞
     * @param worksLikesRecordParam
     */
    @Transactional
    public void likes(WorksLikesRecordParam worksLikesRecordParam){
        if(null == worksLikesRecordParam.getWorksId() || null == worksLikesRecordParam.getUserId()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
        List<WorksLikesRecordResult> worksLikesRecordResults = worksCollectionMapper.getLikesRecord(worksLikesRecordParam);
        if(CollectionUtils.isEmpty(worksLikesRecordResults)){
            WorksLikesRecord worksLikesRecord = new WorksLikesRecord();
            BeanUtilExtend.copyProperties( worksLikesRecordParam,worksLikesRecord,true,null);
            worksLikesRecord.setClickStatus(1);
            worksLikesRecord.setLikesDate(new Date());
            worksCollectionMapper.insertLikesRecord(worksLikesRecord);
            //作品点赞数+1
            WorksCollectionParam worksCollectionParam = new WorksCollectionParam();
            worksCollectionParam.setId(worksLikesRecordParam.getWorksId());
            List<WorksCollectionResult> worksCollectionResults = worksCollectionMapper.findAllByParam(worksCollectionParam);
            WorksCollection worksCollection = new WorksCollection();
            worksCollection.setId(worksLikesRecordParam.getWorksId());
            worksCollection.setLikes(worksCollectionResults.get(0).getLikes()+1);
            worksCollectionMapper.update(worksCollection);
        }else {
            WorksLikesRecordResult worksLikesRecordResult = worksLikesRecordResults.get(0);
            if(worksLikesRecordResult.getClickStatus() == 1){
                throw new CommonException(ResultCodeEnum.ALREADY_LIKES_TODAY);
            }else {
                WorksLikesRecord worksLikesRecord = new WorksLikesRecord();
                BeanUtilExtend.copyProperties( worksLikesRecordResult,worksLikesRecord,true,null);
                worksLikesRecord.setClickStatus(1);
                worksCollectionMapper.updateLikesRecord(worksLikesRecord);
                //作品点赞数+1
                WorksCollectionParam worksCollectionParam = new WorksCollectionParam();
                worksCollectionParam.setId(worksLikesRecordParam.getWorksId());
                List<WorksCollectionResult> worksCollectionResults = worksCollectionMapper.findAllByParam(worksCollectionParam);
                WorksCollection worksCollection = new WorksCollection();
                worksCollection.setId(worksLikesRecordParam.getWorksId());
                worksCollection.setLikes(worksCollectionResults.get(0).getLikes()+1);
                worksCollectionMapper.update(worksCollection);
            }
        }

    }

    /**
     * 取消点赞
     * @param worksLikesRecordParam
     */
    @Transactional
    public void dislikes(WorksLikesRecordParam worksLikesRecordParam){
        if(null == worksLikesRecordParam.getWorksId() || null == worksLikesRecordParam.getUserId()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
        List<WorksLikesRecordResult> worksLikesRecordResults = worksCollectionMapper.getLikesRecord(worksLikesRecordParam);
        if(!CollectionUtils.isEmpty(worksLikesRecordResults)){
            WorksLikesRecordResult worksLikesRecordResult = worksLikesRecordResults.get(0);
            WorksLikesRecord worksLikesRecord = new WorksLikesRecord();
            BeanUtilExtend.copyProperties( worksLikesRecordResult,worksLikesRecord,true,null);
            worksLikesRecord.setClickStatus(0);
            worksCollectionMapper.updateLikesRecord(worksLikesRecord);
            //作品点赞数-1
            WorksCollectionParam worksCollectionParam = new WorksCollectionParam();
            worksCollectionParam.setId(worksLikesRecordParam.getWorksId());
            List<WorksCollectionResult> worksCollectionResults = worksCollectionMapper.findAllByParam(worksCollectionParam);
            WorksCollection worksCollection = new WorksCollection();
            worksCollection.setId(worksLikesRecordParam.getWorksId());
            worksCollection.setLikes(worksCollectionResults.get(0).getLikes()-1);
            worksCollectionMapper.update(worksCollection);
        }

    }

    @PagingQuery
    public PageBean getRecommendedWorks(WorksCollectionParam worksCollectionParam){
        /*if(null == worksCollectionParam.getCurrentUserId()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }*/
        /*int quarter = DateUtil.getQuarter();
        String [] arround = DateUtil.getCurrQuarterFirstDayAndLastDay(quarter);*/
        worksCollectionParam.setOrderBy("likes desc");
        List<WorksCollectionResult> worksCollectionResults = worksCollectionMapper.getRecommendedWorks(worksCollectionParam);
        if(null!=worksCollectionParam.getCurrentUserId()){
            for (WorksCollectionResult worksCollectionResult : worksCollectionResults){
                WorksLikesRecordParam worksLikesRecordParam = new WorksLikesRecordParam();
                worksLikesRecordParam.setWorksId(worksCollectionResult.getId());
                worksLikesRecordParam.setUserId(worksCollectionParam.getCurrentUserId());
                List<WorksLikesRecordResult> worksLikesRecordResults = worksCollectionMapper.getLikesRecord(worksLikesRecordParam);
                if(!CollectionUtils.isEmpty(worksLikesRecordResults)){
                    worksCollectionResult.setCurrentUserLikesStatus(worksLikesRecordResults.get(0).getClickStatus());
                }
            }
        }
        convertUploadFile(worksCollectionResults);
        return new PageBean(worksCollectionResults);
    }

    public static void main(String[] args) {
        int quarter = DateUtil.getQuarter();
        String [] arround = DateUtil.getCurrQuarterFirstDayAndLastDay(quarter);
        System.out.println(arround[0] +"=||"+arround[1]);
    }
}
