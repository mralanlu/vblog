package com.northbund.vblog.service;

import com.northbund.vblog.mapper.CommonFileUploadMapper;
import com.northbund.vblog.pojo.entity.CommonFileUpload;
import com.northbund.vblog.pojo.param.CommonFileUploadParam;
import com.northbund.vblog.pojo.vo.CommonFileUploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 由GenJavaCode类自动生成
 * <p>
 * </p>
 * @date 2019-08-27 15:02
 * @author D1M
 */
@Slf4j
@Service
public class CommonFileUploadService {


    @Autowired
    private CommonFileUploadMapper commonFileUploadMapper;

    public List<CommonFileUploadResult> getByRelIdAndAttachmentType (Long relId, int attachmentType){
        CommonFileUploadParam commonFileUploadParam = new CommonFileUploadParam();
        commonFileUploadParam.setRelId(relId);
        commonFileUploadParam.setAttachmentType(attachmentType);
        return commonFileUploadMapper.findAllByParams(commonFileUploadParam);
    }

    public void deleteByIds(List<Long> ids) {
        for(Long id : ids){
            commonFileUploadMapper.deleteById(id);
        }
    }

    public void deleteByRelIdAndAttachmentType(Long relId,int attachmentType) {
        commonFileUploadMapper.deleteByRelIdAndAttachmentType(relId,attachmentType);
    }

    public List<CommonFileUpload> save(List<CommonFileUpload> commonFileUploads, Long relId, int attachmentType){
        if(CollectionUtils.isEmpty(commonFileUploads)){
            deleteByRelIdAndAttachmentType(relId,attachmentType);
        }else {
            List<Long>  oldIds = new ArrayList<>();
            List<CommonFileUploadResult> commonFileUploadResults = new ArrayList<>();
            CommonFileUploadParam commonFileUploadParam = new CommonFileUploadParam();
            commonFileUploadParam.setRelId(relId);
            commonFileUploadParam.setAttachmentType(attachmentType);
            commonFileUploadResults = findAllCommonFileUpload(commonFileUploadParam);
            if (!CollectionUtils.isEmpty(commonFileUploadResults)){
                for (CommonFileUploadResult commonFileUploadResult:commonFileUploadResults){
                    oldIds.add(commonFileUploadResult.getId());
                }
            }
            for(CommonFileUpload commonFileUpload : commonFileUploads){
                commonFileUpload.setRelId(relId);
                commonFileUpload.setAttachmentType(attachmentType);
                if(null!=commonFileUpload.getId()){
                    oldIds.remove(commonFileUpload.getId());
                    commonFileUploadMapper.update(commonFileUpload);
                }else {
                    commonFileUpload.setUploadAt(new Date());
                    commonFileUploadMapper.insert(commonFileUpload);
                }
            }
            if(!CollectionUtils.isEmpty(oldIds)){
                List<Long> removeIds = new ArrayList<>();
                for (Long id : oldIds){
                    removeIds.add(id);
                }
                deleteByIds(removeIds);
            }
            return commonFileUploads;
        }
        return null;
    }


    public List<CommonFileUploadResult> findAllCommonFileUpload(CommonFileUploadParam  commonFileUploadParam) {
        return commonFileUploadMapper.findAllByParams(commonFileUploadParam);
    }


}
